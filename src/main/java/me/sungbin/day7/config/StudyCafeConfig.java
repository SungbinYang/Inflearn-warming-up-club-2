package me.sungbin.day7.config;

import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.LockerPolicyType;
import me.sungbin.day7.model.StudyCafePassType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudyCafeConfig {

    private final Map<StudyCafePassType, LockerPolicyType> lockerPolicyMap = new HashMap<>();
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

    // 초기 설정에서 기본 정책을 설정
    public StudyCafeConfig() {
        lockerPolicyMap.put(StudyCafePassType.HOURLY, LockerPolicyType.NOT_ALLOWED);
        lockerPolicyMap.put(StudyCafePassType.WEEKLY, LockerPolicyType.ALLOWED);
        lockerPolicyMap.put(StudyCafePassType.FIXED, LockerPolicyType.ALLOWED);
    }

    public Map<StudyCafePassType, LockerPolicyType> getLockerPolicyMap() {
        return lockerPolicyMap;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }

    public StudyCafeFileHandler getFileHandler() {
        return fileHandler;
    }

    public Map<StudyCafePassType, StudyCafePassType> getStrategyMap() {
        return Arrays.stream(StudyCafePassType.values())
                .collect(Collectors.toMap(
                        passType -> passType,
                        passType -> passType
                ));
    }
}

