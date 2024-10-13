package me.sungbin.day12.studycafe.io;

import me.sungbin.day12.studycafe.model.order.StudyCafePassOrder;
import me.sungbin.day12.studycafe.model.pass.StudyCafePass;
import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;
import me.sungbin.day12.studycafe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputHandlerTest {

    private final OutputHandler outputHandler = new OutputHandler();

    @Test
    @DisplayName("프리미엄 스터디카페 환영 메시지가 출력된다")
    void shouldDisplayWelcomeMessage() {
        // When: 환영 메시지 표시
        String output = captureOutput(outputHandler::showWelcomeMessage);

        // Then: 올바른 환영 메시지가 출력됨
        assertThat(output).isEqualTo("*** 프리미엄 스터디카페 ***");
    }

    @Test
    @DisplayName("이용권과 사물함 안내 메시지가 출력된다")
    void shouldDisplayAnnouncementMessage() {
        // When: 공지사항 표시
        String output = captureOutput(outputHandler::showAnnouncement);

        // Then: 올바른 안내 메시지가 출력됨
        assertThat(output).contains(
                "사물함은 고정석 선택 시 이용 가능합니다",
                "2주권 이상 결제 시 10% 할인"
        );
    }

    @Test
    @DisplayName("이용권 선택을 요청하는 메시지가 출력된다")
    void shouldAskForPassTypeSelection() {
        // When: 이용권 선택 요청 메시지 표시
        String output = captureOutput(outputHandler::askPassTypeSelection);

        // Then: 이용권 선택 안내 메시지가 출력됨
        assertThat(output).contains("1. 시간 이용권(자유석)", "2. 주단위 이용권(자유석)", "3. 1인 고정석");
    }

    @Test
    @DisplayName("이용권 목록이 올바르게 출력된다")
    void shouldDisplayPassListForSelection() {
        // Given: 이용권 목록 생성
        StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 5, 10000, 0.1);
        StudyCafeSeatPass weeklyPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.15);

        // When: 이용권 목록 출력
        String output = captureOutput(() -> outputHandler.showPassListForSelection(List.of(hourlyPass, weeklyPass)));

        // Then: 올바른 이용권 목록이 출력됨
        assertThat(output).contains("1. 5시간권 - 10000원", "2. 7주권 - 20000원");
    }

    @Test
    @DisplayName("사물함 이용 여부를 묻는 메시지가 출력된다")
    void shouldAskForLockerPass() {
        // Given: 고정석 사물함 이용권 준비
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);

        // When: 사물함 이용 여부 메시지 출력
        String output = captureOutput(() -> outputHandler.askLockerPass(lockerPass));

        // Then: 올바른 사물함 메시지가 출력됨
        assertThat(output).contains("사물함을 이용하시겠습니까?", "30주권 - 50000원", "1. 예 | 2. 아니오");
    }

    @Test
    @DisplayName("이용권 주문 내역이 올바르게 출력된다")
    void shouldDisplayPassOrderSummary() {
        // Given: 이용권과 사물함 포함된 주문 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 50000);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // When: 주문 내역 출력
        String output = captureOutput(() -> outputHandler.showPassOrderSummary(passOrder));

        // Then: 주문 내역이 올바르게 출력됨
        assertThat(output).contains(
                "이용권: 7주권 - 20000원",
                "사물함: 30주권 - 50000원",
                "이벤트 할인 금액: 2000원",
                "총 결제 금액: 68000원"
        );
    }

    @Test
    @DisplayName("이용권 주문 내역에 사물함이 없을 경우 올바르게 출력된다")
    void shouldDisplayPassOrderSummaryWithoutLocker() {
        // Given: 사물함 없이 주문 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.1);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, null);

        // When: 주문 내역 출력
        String output = captureOutput(() -> outputHandler.showPassOrderSummary(passOrder));

        // Then: 사물함 없이 올바른 주문 내역 출력됨
        assertThat(output).contains(
                "이용권: 7주권 - 20000원",
                "이벤트 할인 금액: 2000원",
                "총 결제 금액: 18000원"
        );
    }

    @Test
    @DisplayName("간단한 메시지가 올바르게 출력된다")
    void shouldDisplaySimpleMessage() {
        // Given: 출력할 메시지
        String message = "결제가 완료되었습니다.";

        // When: 간단한 메시지 출력
        String output = captureOutput(() -> outputHandler.showSimpleMessage(message));

        // Then: 올바른 메시지가 출력됨
        assertThat(output).isEqualTo("결제가 완료되었습니다.");
    }

    @Test
    @DisplayName("예상되지 않는 이용권 타입인 경우 빈 문자열을 반환해야 한다")
    void shouldReturnEmptyStringWhenUnexpectedPassType() throws Exception {
        // Given: 알 수 없는 StudyCafePassType을 가진 목(Mock) 이용권 준비
        StudyCafePass unknownPass = mock(StudyCafePass.class);
        when(unknownPass.getPassType()).thenReturn(null);  // 알 수 없는 타입
        when(unknownPass.getDuration()).thenReturn(0);
        when(unknownPass.getPrice()).thenReturn(0);

        // OutputHandler 인스턴스 생성
        OutputHandler outputHandler = new OutputHandler();

        // Reflection을 통해 private 메서드에 접근
        Method displayMethod = OutputHandler.class.getDeclaredMethod("display", StudyCafePass.class);
        displayMethod.setAccessible(true);  // 접근 허용

        // When: display 메서드 호출
        String result = (String) displayMethod.invoke(outputHandler, unknownPass);

        // Then: 빈 문자열을 반환해야 함
        assertThat(result).isEqualTo("");
    }

    @Test
    @DisplayName("이용 내역에 이벤트 할인 금액이 표시된다 - 할인 적용된 이용권")
    void shouldDisplayDiscountPriceWhenApplicable() {
        // Given: 할인 적용된 주 단위 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 7, 20000, 0.1);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        OutputHandler outputHandler = new OutputHandler();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // When: 이용 내역 출력
        outputHandler.showPassOrderSummary(order);

        // Then: 할인 금액이 포함된지 검증
        String output = outputStream.toString();
        assertThat(output).contains("이벤트 할인 금액: 2000원");
    }

    @Test
    @DisplayName("이용 내역에 이벤트 할인 금액이 표시되지 않는다 - 할인 없는 이용권")
    void shouldNotDisplayDiscountPriceWhenNotApplicable() {
        // Given: 할인 없는 시간 단위 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 3, 3000, 0.0);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        OutputHandler outputHandler = new OutputHandler();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // When: 이용 내역 출력
        outputHandler.showPassOrderSummary(order);

        // Then: 할인 금액이 포함되지 않았는지 검증
        String output = outputStream.toString();
        assertThat(output).doesNotContain("이벤트 할인 금액");
    }

    @Test
    @DisplayName("이용 내역에 사물함 이용 정보가 표시된다")
    void shouldDisplayLockerPassWhenAvailable() {
        // Given: 고정석 이용권과 사물함 이용권 생성
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        OutputHandler outputHandler = new OutputHandler();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // When: 이용 내역 출력
        outputHandler.showPassOrderSummary(order);

        // Then: 사물함 이용권 정보가 포함되는지 검증
        String output = outputStream.toString();
        assertThat(output).contains("사물함: 30주권 - 10000원");
    }

    @Test
    @DisplayName("이용 내역에 사물함 이용 정보가 표시되지 않는다")
    void shouldNotDisplayLockerPassWhenNotAvailable() {
        // Given: 고정석 이용권만 생성 (사물함 이용권 없음)
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 50000, 0.1);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        OutputHandler outputHandler = new OutputHandler();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // When: 이용 내역 출력
        outputHandler.showPassOrderSummary(order);

        // Then: 사물함 정보가 포함되지 않았는지 검증
        String output = outputStream.toString();
        assertThat(output).doesNotContain("사물함:");
    }

    private String captureOutput(Runnable runnable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        runnable.run();
        return outputStream.toString().trim();
    }
}
