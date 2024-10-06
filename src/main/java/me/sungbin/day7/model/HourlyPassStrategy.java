package me.sungbin.day7.model;

import me.sungbin.day7.io.OutputHandler;

public class HourlyPassStrategy implements StudyCafePassStrategy {

    private final StudyCafePassHelper passHelper;

    public HourlyPassStrategy(OutputHandler outputHandler) {
        this.passHelper = new StudyCafePassHelper(outputHandler);
    }

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        passHelper.showPassOrderSummary(selectedPass, null);
    }
}
