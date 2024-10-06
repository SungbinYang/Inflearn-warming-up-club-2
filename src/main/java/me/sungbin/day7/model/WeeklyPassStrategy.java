package me.sungbin.day7.model;

import me.sungbin.day7.io.OutputHandler;

public class WeeklyPassStrategy implements StudyCafePassStrategy {

    private final StudyCafePassHelper passHelper;

    public WeeklyPassStrategy(OutputHandler outputHandler) {
        this.passHelper = new StudyCafePassHelper(outputHandler);
    }

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        // 주단위 이용권 처리: 사물함 없음
        passHelper.showPassOrderSummary(selectedPass, null);
    }
}
