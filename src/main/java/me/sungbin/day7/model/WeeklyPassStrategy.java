package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.OutputHandler;

public class WeeklyPassStrategy implements StudyCafePassStrategy {
    @Override
    public void handlePass(StudyCafePass selectedPass) {
        StudyCafeConfigProvider.getConfig().getOutputHandler().showPassOrderSummary(selectedPass);
    }
}