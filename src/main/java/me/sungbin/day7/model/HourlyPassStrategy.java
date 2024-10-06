package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;

public class HourlyPassStrategy implements StudyCafePassStrategy {

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        StudyCafeConfigProvider.getConfig().getOutputHandler().showPassOrderSummary(selectedPass);
    }
}
