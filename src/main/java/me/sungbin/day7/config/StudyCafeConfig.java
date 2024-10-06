package me.sungbin.day7.config;

import me.sungbin.day7.io.handler.ConsoleInputHandler;
import me.sungbin.day7.io.handler.ConsoleOutputHandler;
import me.sungbin.day7.io.handler.InputHandler;
import me.sungbin.day7.io.handler.OutputHandler;
import me.sungbin.day7.io.provider.StudyCafeLockerPassFileReadProvider;
import me.sungbin.day7.io.provider.StudyCafeLockerReadProvider;
import me.sungbin.day7.io.provider.StudyCafeSeatPassFileReadProvider;
import me.sungbin.day7.io.provider.StudyCafeSeatReadProvider;
import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.locker.policy.LockerPolicyType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudyCafeConfig {

    private final Map<StudyCafePassType, LockerPolicyType> lockerPolicyMap = new HashMap<>();
    private final InputHandler inputHandler = new ConsoleInputHandler();
    private final OutputHandler outputHandler = new ConsoleOutputHandler();
    private final StudyCafeSeatReadProvider studyCafeSeatReadProvider = new StudyCafeSeatPassFileReadProvider();
    private final StudyCafeLockerReadProvider studyCafeLockerReadProvider = new StudyCafeLockerPassFileReadProvider();

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

    public StudyCafeSeatReadProvider getStudyCafeSeatReadProvider() {
        return studyCafeSeatReadProvider;
    }

    public StudyCafeLockerReadProvider getStudyCafeLockerReadProvider() {
        return studyCafeLockerReadProvider;
    }

    public Map<StudyCafePassType, StudyCafePassType> getStrategyMap() {
        return Arrays.stream(StudyCafePassType.values())
                .collect(Collectors.toMap(
                        passType -> passType,
                        passType -> passType
                ));
    }
}

