package me.sungbin.day7.config;

import me.sungbin.day7.io.ConsoleInputHandler;
import me.sungbin.day7.io.ConsoleOutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.pass.locker.policy.LockerPolicyType;
import me.sungbin.day7.model.pass.StudyCafePassType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudyCafeConfig {

    private final Map<StudyCafePassType, LockerPolicyType> lockerPolicyMap = new HashMap<>();
    private final ConsoleInputHandler inputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler outputHandler = new ConsoleOutputHandler();
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

    public ConsoleInputHandler getInputHandler() {
        return inputHandler;
    }

    public ConsoleOutputHandler getOutputHandler() {
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

