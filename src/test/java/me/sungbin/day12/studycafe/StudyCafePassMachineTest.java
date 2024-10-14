package me.sungbin.day12.studycafe;

import me.sungbin.day12.studycafe.io.StudyCafeIOHandler;
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
import static org.mockito.Mockito.mock;

class StudyCafePassMachineTest {

    private final StudyCafeIOHandler ioHandler = mock(StudyCafeIOHandler.class);
    private final SeatPassProvider seatPassProvider = mock(SeatPassProvider.class);
    private final LockerPassProvider lockerPassProvider = mock(LockerPassProvider.class);
    private final StudyCafePassMachine machine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

    @Test
    @DisplayName("고정석 이용권 주문 시 사물함 없이도 주문 요약이 정상 출력된다")
    void shouldCallShowPassOrderSummaryWithoutLocker() {
        // given: 고정석 이용권을 선택하고 사물함을 사용하지 않음
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 2000, 0.2);
        StudyCafePassMachine machine = createMachineWithoutLocker(seatPass, new StudyCafeIOHandler());

        // when: 주문 실행
        // then: 예외 없이 주문 요약이 출력됨
        assertThatCode(machine::run).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("고정석 이용권에 사물함을 포함한 주문이 정상 처리된다")
    void shouldCallShowPassOrderSummaryWithLocker() {
        // given: 고정석 이용권과 사물함 이용권을 선택함
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 2000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 2000);
        StudyCafePassMachine machine = createMachineWithLocker(seatPass, lockerPass, new StudyCafeIOHandler());

        // when: 주문 실행
        // then: 예외 없이 주문 요약이 정상 출력됨
        assertThatCode(machine::run).doesNotThrowAnyException();
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
    @DisplayName("고정석 이용권 선택 시 사물함 이용이 가능하다")
    void shouldReturnLockerPassWhenSelectedForFixedSeatPass() throws Exception {
        // given: 사용자 입력을 통해 고정석 이용권과 사물함을 선택함
        String simulatedInput = "1\n";  // 1: 사물함 사용 선택
        setSystemInput(simulatedInput);

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);

        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(
                List.of(StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000))
        );
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(fixedSeatPass));

        StudyCafePassMachine passMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

        // when: private 메서드 selectLockerPass 호출 (Reflection 사용)
        Method selectLockerPassMethod = StudyCafePassMachine.class.getDeclaredMethod(
                "selectLockerPass", StudyCafeSeatPass.class
        );
        selectLockerPassMethod.setAccessible(true);

        // Optional<StudyCafeLockerPass>로 안전하게 캐스팅
        Object result = selectLockerPassMethod.invoke(passMachine, fixedSeatPass);
        Optional<StudyCafeLockerPass> lockerPass = castToOptional(result);

        // then: 사물함 이용권이 선택되었는지 확인
        assertThat(lockerPass).isPresent();
        assertThat(lockerPass.get().getPassType()).isEqualTo(StudyCafePassType.FIXED);
    }

    @Test
    @DisplayName("자유석 이용권 선택 시 사물함 이용이 불가능하다")
    void shouldReturnEmptyWhenLockerNotAvailableForSeatPass() throws Exception {
        // given: 자유석 이용권을 선택함
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(List.of());  // 사물함 없음
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(hourlyPass));

        StudyCafePassMachine passMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);

        // when: private 메서드 selectLockerPass 호출 (Reflection 사용)
        Method selectLockerPassMethod = StudyCafePassMachine.class.getDeclaredMethod(
                "selectLockerPass", StudyCafeSeatPass.class
        );
        selectLockerPassMethod.setAccessible(true);

        // 안전한 타입 캐스팅을 통해 Optional<StudyCafeLockerPass>로 변환
        Optional<StudyCafeLockerPass> result = castToOptional(
                selectLockerPassMethod.invoke(passMachine, hourlyPass)
        );

        // then: 빈 Optional이 반환됨
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("고정석 이용권 선택 후 사물함 사용을 선택하면 사물함 이용권이 반환된다")
    void shouldReturnLockerPassWhenLockerSelectedForFixedSeatPass() throws Exception {
        // given: 사용자 입력으로 사물함 사용을 선택함
        setSystemInput("1\n");  // 1: 사물함 사용 선택

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // 고정석과 사물함 이용권을 포함한 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        // then: 사물함 이용권이 반환됨
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass);
    }

    @Test
    @DisplayName("고정석 이용권 선택 후 사물함 사용을 선택하지 않으면 빈 Optional을 반환한다")
    void shouldReturnEmptyWhenLockerNotSelectedForFixedSeatPass() throws Exception {
        // given: 사용자 입력으로 사물함 사용을 선택하지 않음
        setSystemInput("2\n");  // 2: 사물함 사용 안 함

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // 고정석과 사물함 이용권을 포함한 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        // then: 빈 Optional이 반환됨
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("자유석 이용권은 사물함 사용이 불가능하므로 빈 Optional을 반환한다")
    void shouldReturnEmptyWhenLockerNotAvailableForNonFixedSeatPass() throws Exception {
        // given: 자유석 이용권을 선택함
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        // 자유석 이용권만 포함한 StudyCafePassMachine 생성 (사물함 없음)
        StudyCafePassMachine passMachine = createPassMachine(List.of(hourlyPass), List.of());

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, hourlyPass);

        // then: 빈 Optional이 반환됨
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("고정석 이용권에 사물함 이용권이 존재하고 사용을 선택하면 사물함 이용권이 반환된다")
    void shouldReturnLockerPassWhenPresentAndSelected() throws Exception {
        // given: 사용자 입력으로 사물함 사용을 선택함
        setSystemInput("1\n");  // 1: 사물함 사용 선택

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // 고정석과 사물함 이용권을 포함한 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        // then: 사물함 이용권이 반환됨
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(lockerPass);
    }

    @Test
    @DisplayName("고정석 이용권에 사물함이 있지만 사용하지 않으면 사물함이 제공되지 않는다")
    void shouldNotReturnLockerPassWhenLockerNotSelected() throws Exception {
        // given: 사용자 입력으로 사물함 사용을 선택하지 않음
        setSystemInput("2\n");  // 2: 사물함 사용 안 함

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // 고정석과 사물함 이용권을 포함한 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of(lockerPass));

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        // then: 사물함이 제공되지 않음
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("사물함 이용권이 없는 고정석 이용권을 선택하면 사물함이 제공되지 않는다")
    void shouldNotReturnLockerPassWhenNoLockerPassAvailable() throws Exception {
        // given: 사용자 입력으로 사물함 사용을 시도하지만 사물함 이용권이 없음
        setSystemInput("1\n");  // 1: 사물함 사용 시도

        StudyCafeSeatPass fixedSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);

        // 사물함 없이 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedSeatPass), List.of());

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, fixedSeatPass);

        // then: 사물함이 제공되지 않음
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("자유석 이용권은 사물함을 제공하지 않는다")
    void shouldNotReturnLockerPassForNonFixedSeatPass() throws Exception {
        // given: 자유석 이용권을 선택함 (사물함 이용 불가)
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);

        // 자유석 이용권만 포함된 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(hourlyPass), List.of());

        // when: 사물함 선택 메서드 호출
        Optional<StudyCafeLockerPass> result = invokeSelectLockerPass(passMachine, hourlyPass);

        // then: 사물함이 제공되지 않음
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("스터디카페 이용권과 사물함 선택이 정상 처리된다")
    void shouldRunPassMachineSuccessfully() {
        // given: 사용자 입력으로 고정석과 사물함 사용을 선택함
        setSystemInput("3\n1\n");  // 3: 고정석 선택, 1: 사물함 사용 선택

        StudyCafeSeatPass fixedPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.2);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // 고정석과 사물함 이용권을 포함한 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(fixedPass), List.of(lockerPass));

        // when & then: run 메서드 실행 시 예외가 발생하지 않음
        assertThatCode(passMachine::run).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("잘못된 입력이 들어와도 시스템이 정상적으로 처리된다")
    void shouldHandleInvalidInputGracefully() {
        // given: 유효하지 않은 사용자 입력을 설정
        setSystemInput("4\n");  // 4: 잘못된 선택

        // 빈 이용권 목록을 사용해 StudyCafePassMachine 생성
        StudyCafePassMachine passMachine = createPassMachine(List.of(), List.of());

        // when & then: run 메서드 실행 시 예외가 발생하지 않음
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

    // 사물함이 없는 경우
    private StudyCafePassMachine createMachineWithoutLocker(StudyCafeSeatPass seatPass, StudyCafeIOHandler ioHandler) {
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(seatPass));
        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(List.of());  // 빈 사물함 리스트

        return new StudyCafePassMachine(seatPassProvider, lockerPassProvider);
    }

    // 사물함이 있는 경우
    private StudyCafePassMachine createMachineWithLocker(
            StudyCafeSeatPass seatPass,
            StudyCafeLockerPass lockerPass,
            StudyCafeIOHandler ioHandler
    ) {
        SeatPassProvider seatPassProvider = () -> StudyCafeSeatPasses.of(List.of(seatPass));
        LockerPassProvider lockerPassProvider = () -> StudyCafeLockerPasses.of(List.of(lockerPass));

        return new StudyCafePassMachine(seatPassProvider, lockerPassProvider);
    }

    private Optional<StudyCafeLockerPass> castToOptional(Object obj) {
        if (obj instanceof Optional<?>) {
            Optional<?> optional = (Optional<?>) obj;
            if (optional.isPresent() && optional.get() instanceof StudyCafeLockerPass) {
                return optional.map(o -> (StudyCafeLockerPass) o);
            }
        }
        return Optional.empty();
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
}
