package me.sungbin.day7.config;

import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.*;

import java.util.Map;

public class StudyCafeConfig {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }

    public StudyCafeFileHandler getFileHandler() {
        return fileHandler;
    }

    public Map<StudyCafePassType, StudyCafePassStrategy> getStrategyMap() {
        return Map.of(
                StudyCafePassType.FIXED, new FixedPassStrategy(),
                StudyCafePassType.HOURLY, new HourlyPassStrategy(),
                StudyCafePassType.WEEKLY, new WeeklyPassStrategy()
        );
    }
}
