package me.sungbin.day7.io;

import me.sungbin.day7.model.order.Order;
import me.sungbin.day7.model.pass.StudyCafePass;
import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;

import java.util.List;

public class ConsoleOutputHandler implements OutputHandler {

    @Override
    public void showWelcomeMessage() {
        System.out.println("*** 프리미엄 스터디카페 ***");
    }

    @Override
    public void showAnnouncement() {
        System.out.println("* 사물함은 고정석 선택 시 이용 가능합니다. (추가 결제)");
        System.out.println("* !오픈 이벤트! 2주권 이상 결제 시 10% 할인, 12주권 결제 시 15% 할인! (결제 시 적용)");
        System.out.println();
    }

    @Override
    public void askPassTypeSelection() {
        System.out.println("사용하실 이용권을 선택해 주세요.");
        System.out.println("1. 시간 이용권(자유석) | 2. 주단위 이용권(자유석) | 3. 1인 고정석");
    }

    @Override
    public void showPassListForSelection(List<StudyCafeSeatPass> passes) {
        System.out.println();
        System.out.println("이용권 목록");

        for (int index = 0; index < passes.size(); index++) {
            StudyCafeSeatPass pass = passes.get(index);
            System.out.println(String.format("%s. ", index + 1) + display(pass));
        }
    }

    @Override
    public void askLockerPass(StudyCafeLockerRentalPass lockerPass) {
        System.out.println();
        String askMessage = String.format(
                "사물함을 이용하시겠습니까? (%s)", display(lockerPass)
        );

        System.out.println(askMessage);
        System.out.println("1. 예 | 2. 아니오");
    }

    @Override
    public void showPassOrderSummary(Order order) {
        System.out.println();
        System.out.println("이용 내역");
        System.out.println("이용권: " + display(order.getPass()));

        if (order.getLockerPass() != null) {
            System.out.println("사물함: " + display(order.getLockerPass()));
        }

        int discountPrice = order.getDiscountPrice();

        if (discountPrice > 0) {
            System.out.println("이벤트 할인 금액: " + discountPrice + "원");
        }

        int totalPrice = order.getTotalPrice();

        System.out.println("총 결제 금액: " + totalPrice + "원");
        System.out.println();
    }

    @Override
    public void showSimpleMessage(String message) {
        System.out.println(message);
    }

    private String display(StudyCafePass pass) {
        StudyCafePassType passType = pass.getPassType();
        int duration = pass.getDuration();
        int price = pass.getPrice();

        return switch (passType) {
            case HOURLY -> String.format("%s시간권 - %d원", duration, price);
            case WEEKLY, FIXED -> String.format("%s주권 - %d원", duration, price);
        };
    }
}
