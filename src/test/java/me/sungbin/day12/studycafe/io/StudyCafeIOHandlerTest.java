package me.sungbin.day12.studycafe.io;

import me.sungbin.day12.studycafe.model.order.StudyCafePassOrder;
import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeIOHandlerTest {

    private ByteArrayOutputStream captureOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    private void provideInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("고정석 이용권 선택 시 FIXED 유형을 반환해야 한다")
    void shouldReturnFixedPassTypeWhenUserSelectsFixedPass() {
        // Given: 사용자 입력을 3으로 설정
        provideInput("3\n");
        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

        // When: 이용권 유형 선택
        StudyCafePassType result = ioHandler.askPassTypeSelecting();

        // Then: FIXED 유형 반환 확인
        assertThat(result).isEqualTo(StudyCafePassType.FIXED);
    }

    @Test
    @DisplayName("사용자가 두 번째 이용권을 선택하면 해당 이용권이 반환된다")
    void shouldReturnSelectedSeatPass() {
        // Given: 사용자 입력을 2로 설정
        provideInput("2\n");
        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

        StudyCafeSeatPass firstPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafeSeatPass secondPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15);

        // When: 이용권 선택
        StudyCafeSeatPass result = ioHandler.askPassSelecting(List.of(firstPass, secondPass));

        // Then: 두 번째 이용권 반환 확인
        assertThat(result).isEqualTo(secondPass);
    }

    @Test
    @DisplayName("사물함 이용 선택 시 true가 반환된다")
    void shouldReturnTrueWhenUserSelectsLocker() {
        // Given: 사용자 입력을 1로 설정 (락커 이용 선택)
        provideInput("1\n");
        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // When: 락커 선택
        boolean result = ioHandler.askLockerPass(lockerPass);

        // Then: true 반환 확인
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사물함 이용 미선택 시 false가 반환된다")
    void shouldReturnFalseWhenUserDeclinesLocker() {
        // Given: 사용자 입력을 2로 설정 (락커 미선택)
        provideInput("2\n");
        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);

        // When: 락커 선택
        boolean result = ioHandler.askLockerPass(lockerPass);

        // Then: false 반환 확인
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("이용 내역 요약이 올바르게 출력된다")
    void shouldDisplayPassOrderSummary() {
        // Given: 주문 생성 및 출력 캡처
        ByteArrayOutputStream output = captureOutput();
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

        // When: 이용 내역 출력
        ioHandler.showPassOrderSummary(order);

        // Then: 출력 내용 검증
        String outputContent = output.toString();
        assertThat(outputContent).contains("이용 내역");
        assertThat(outputContent).contains("이벤트 할인 금액: 5000원");
        assertThat(outputContent).contains("총 결제 금액: 55000원");
    }

    @Test
    @DisplayName("간단한 메시지가 올바르게 출력된다")
    void shouldDisplaySimpleMessage() {
        // Given: 메시지와 출력 캡처
        ByteArrayOutputStream output = captureOutput();
        String message = "결제가 완료되었습니다.";
        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

        // When: 메시지 출력
        ioHandler.showSimpleMessage(message);

        // Then: 출력된 메시지 검증
        assertThat(output.toString()).contains(message);
    }

    @Test
    @DisplayName("사용자에게 웰컴 메시지를 정확히 출력해야 한다")
    void shouldDisplayWelcomeMessage() {
        // Given: System.out을 가로채기 위한 설정
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

        // When: 웰컴 메시지 출력 메서드 호출
        ioHandler.showWelcomeMessage();

        // Then: 예상된 메시지가 출력되었는지 검증
        String output = outputStream.toString().trim();
        assertThat(output).isEqualTo("*** 프리미엄 스터디카페 ***");
    }

    @Test
    @DisplayName("사용자에게 공지 사항을 정확히 출력해야 한다")
    void shouldDisplayAnnouncement() {
        // Given: System.out을 가로채기 위한 설정
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();

        // When: 공지 사항 출력 메서드 호출
        ioHandler.showAnnouncement();

        // Then: 예상된 공지 사항이 출력되었는지 검증
        String output = outputStream.toString().trim();
        assertThat(output).contains(
                "* 사물함은 고정석 선택 시 이용 가능합니다. (추가 결제)",
                "* !오픈 이벤트! 2주권 이상 결제 시 10% 할인, 12주권 결제 시 15% 할인!"
        );
    }
}
