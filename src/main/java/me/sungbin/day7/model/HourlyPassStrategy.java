package me.sungbin.day7.model;

import me.sungbin.day7.io.OutputHandler;

public class HourlyPassStrategy implements StudyCafePassStrategy {

    private final OutputHandler outputHandler;

    public HourlyPassStrategy(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        outputHandler.showPassOrderSummary(selectedPass, null);
    }
}
