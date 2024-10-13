package me.sungbin.day12.studycafe;

import me.sungbin.day12.studycafe.io.StudyCafeIOHandler;
import me.sungbin.day12.studycafe.model.order.StudyCafePassOrder;
import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPasses;
import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPass;
import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPasses;
import me.sungbin.day12.studycafe.provider.LockerPassProvider;
import me.sungbin.day12.studycafe.provider.SeatPassProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

class StudyCafePassMachineTest {

    private final StudyCafeIOHandler ioHandler = mock(StudyCafeIOHandler.class);
    private final SeatPassProvider seatPassProvider = mock(SeatPassProvider.class);
    private final LockerPassProvider lockerPassProvider = mock(LockerPassProvider.class);
    private final StudyCafePassMachine machine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);


    @Test
    @DisplayName("사용자가 고정석 이용권을 선택할 수 있어야 한다")
    void shouldAllowUserToSelectFixedPass() {
        // Given: SeatPassProvider와 LockerPassProvider 구성
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1)
        ));

        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(List.of(
                StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000)
        ));

        StudyCafePassMachine passMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

        // When & Then: 정상적으로 실행되는지 확인
        passMachine.run();  // 수동으로 테스트하며 사용자 입력을 통해 검증
    }

    @Test
    @DisplayName("사용자가 선택한 이용권이 정상적으로 반환된다")
    void shouldReturnSelectedPassSuccessfully() throws Exception {
        // Given: 사용자 입력을 "1"로 모의하여 시간 단위 이용권 선택
        String simulatedInput = "1\n1\n"; // 1: 시간 이용권 선택 -> 첫 번째 이용권 선택
        setSystemInput(simulatedInput);

        // SeatPassProvider와 LockerPassProvider를 모의로 사용하거나 실제 인스턴스 사용
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(
                List.of(StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1))
        );
        StudyCafeSeatPass result = getStudyCafeSeatPass(seatPassProvider);

        // Then: 선택된 이용권이 시간 단위 이용권이어야 함
        assertThat(result).isNotNull();
        assertThat(result.getPassType()).isEqualTo(StudyCafePassType.HOURLY);
    }

    @Test
    @DisplayName("고정석 이용권을 선택한 경우 사물함을 사용할 수 있다")
    void shouldReturnLockerPassWhenSelectedForFixedSeatPass() throws Exception {
        // Given: 사용자 입력을 "1"로 모의 (락커 사용 선택)
        String simulatedInput = "1\n";
        setSystemInput(simulatedInput);

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);

        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(
                List.of(StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000))
        );
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(fixedSeatPass));
        StudyCafePassMachine passMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

        // 리플렉션으로 private 메서드 접근
        Method selectLockerPassMethod = StudyCafePassMachine.class.getDeclaredMethod("selectLockerPass", StudyCafeSeatPass.class);
        selectLockerPassMethod.setAccessible(true);

        // When: selectLockerPass 메서드 호출
        Optional<StudyCafeLockerPass> result =
                (Optional<StudyCafeLockerPass>) selectLockerPassMethod.invoke(passMachine, fixedSeatPass);

        // Then: 사물함 이용권이 선택되었음을 확인
        assertThat(result).isPresent();
        assertThat(result.get().getPassType()).isEqualTo(StudyCafePassType.FIXED);
    }

    @Test
    @DisplayName("고정석이 아닌 이용권을 선택하면 사물함을 이용할 수 없다")
    void shouldReturnEmptyWhenLockerNotAvailableForSeatPass() throws Exception {
        // Given: 자유석 이용권 선택
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(List.of());
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(hourlyPass));
        StudyCafePassMachine passMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

        // 리플렉션으로 private 메서드 접근
        Method selectLockerPassMethod = StudyCafePassMachine.class.getDeclaredMethod("selectLockerPass", StudyCafeSeatPass.class);
        selectLockerPassMethod.setAccessible(true);

        // When: selectLockerPass 메서드 호출
        Optional<StudyCafeLockerPass> result =
                (Optional<StudyCafeLockerPass>) selectLockerPassMethod.invoke(passMachine, hourlyPass);

        // Then: 빈 Optional이 반환되어야 함
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("고정석 이용권을 선택하고 사물함을 선택한 경우 사물함 이용권이 반환된다")
    void shouldReturnLockerPassWhenLockerSelectedForFixedSeatPass() throws Exception {
        setSystemInput("1\n"); // 락커 사용 선택

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass);
    }

    @Test
    @DisplayName("고정석 이용권을 선택했지만 사물함을 선택하지 않은 경우 빈 Optional을 반환한다")
    void shouldReturnEmptyWhenLockerNotSelectedForFixedSeatPass() throws Exception {
        setSystemInput("2\n"); // 락커 사용하지 않음

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("사물함을 이용할 수 없는 자유석 이용권을 선택한 경우 빈 Optional을 반환한다")
    void shouldReturnEmptyWhenLockerNotAvailableForNonFixedSeatPass() throws Exception {
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafePassMachine passMachine = createPassMachine(List.of(hourlyPass), List.of());

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, hourlyPass);

        assertThat(result).isEmpty();
    }

    private StudyCafeSeatPass getStudyCafeSeatPass(SeatPassProvider seatPassProvider) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(List.of());

        StudyCafePassMachine passMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

        // 리플렉션을 통해 private 메서드 selectPass에 접근
        Method selectPassMethod = StudyCafePassMachine.class.getDeclaredMethod("selectPass");
        selectPassMethod.setAccessible(true);

        // When: selectPass 메서드 호출
        return (StudyCafeSeatPass) selectPassMethod.invoke(passMachine);
    }

    @Test
    @DisplayName("고정석 이용권에 대해 사물함 이용권이 존재하고 선택한 경우 사물함이 반환된다")
    void shouldReturnLockerPassWhenPresentAndSelected() throws Exception {
        setSystemInput("1\n"); // 락커 사용 선택

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass);
    }

    @Test
    @DisplayName("고정석 이용권에 대해 사물함이 존재하지만 선택하지 않은 경우 빈 Optional을 반환한다")
    void shouldReturnEmptyWhenLockerNotSelected() throws Exception {
        setSystemInput("2\n"); // 락커 미선택

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("사물함 이용권이 없는 고정석 이용권을 선택한 경우 빈 Optional을 반환한다")
    void shouldReturnEmptyWhenNoLockerPassAvailable() throws Exception {
        setSystemInput("1\n"); // 사용자가 락커 선택을 시도했으나 사물함이 없음

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of());

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("사물함 이용이 불가능한 자유석 이용권을 선택한 경우 빈 Optional을 반환한다")
    void shouldReturnEmptyForNonFixedSeatPass() throws Exception {
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafePassMachine passMachine = createPassMachine(List.of(hourlyPass), List.of());

        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, hourlyPass);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("스터디카페 이용권 선택과 사물함 선택이 정상적으로 수행된다")
    void shouldRunPassMachineSuccessfully() {
        // Given: 사용자 입력 모의 설정 (고정석 선택 및 사물함 사용 선택)
        setSystemInput("3\n1\n");  // 고정석 선택(3), 사물함 사용(1)

        StudyCafeSeatPass fixedPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        StudyCafePassMachine passMachine = createPassMachine(
                List.of(fixedPass), List.of(lockerPass)
        );

        // When & Then: run 메서드 실행 시 예외가 발생하지 않아야 함
        assertThatCode(passMachine::run).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("잘못된 입력 시 AppException에 대한 메시지를 출력한다")
    void shouldHandleInvalidInputGracefully() {
        // Given: 잘못된 사용자 입력 모의 설정
        setSystemInput("4\n");  // 유효하지 않은 선택

        StudyCafePassMachine passMachine = createPassMachine(List.of(), List.of());

        // When & Then: run 메서드 실행 시 예외가 발생하지 않도록 검증
        assertThatCode(passMachine::run).doesNotThrowAnyException();
    }


    // Private 메서드 호출 헬퍼 메서드
    private Optional<StudyCafeLockerPass> invokeSelectLockerPass(StudyCafePassMachine passMachine, StudyCafeSeatPass selectedPass) throws Exception {
        Method method = StudyCafePassMachine.class.getDeclaredMethod("selectLockerPass", StudyCafeSeatPass.class);
        method.setAccessible(true);
        return (Optional<StudyCafeLockerPass>) method.invoke(passMachine, selectedPass);
    }

    // 테스트용 StudyCafePassMachine 인스턴스 생성 헬퍼 메서드
    private StudyCafePassMachine createPassMachine(List<StudyCafeSeatPass> seatPasses, List<StudyCafeLockerPass> lockerPasses) {
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(seatPasses);
        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(lockerPasses);
        return new StudyCafePassMachine(seatPassProvider, lockerPassProvider);
    }

    // System.in을 설정하는 헬퍼 메서드
    private void setSystemInput(String input) {
        InputStream originalIn = System.in;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // 테스트 완료 후 System.in 복원
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.setIn(originalIn)));
    }
}
