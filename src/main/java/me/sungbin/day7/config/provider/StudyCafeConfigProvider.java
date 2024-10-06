package me.sungbin.day7.config.provider;

import me.sungbin.day7.config.StudyCafeConfig;
import me.sungbin.day7.io.handler.InputHandler;
import me.sungbin.day7.io.handler.OutputHandler;
import me.sungbin.day7.io.provider.StudyCafeLockerReadProvider;
import me.sungbin.day7.io.provider.StudyCafeSeatReadProvider;
import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.locker.policy.LockerPolicyType;

import java.util.Map;

public class StudyCafeConfigProvider {
    private static final StudyCafeConfig CONFIG = new StudyCafeConfig();

    public static StudyCafeConfig getConfig() {
        return CONFIG;
    }

    public static InputHandler getInputHandler() {
        return CONFIG.getInputHandler();
    }

    public static OutputHandler getOutputHandler() {
        return CONFIG.getOutputHandler();
    }

    public static StudyCafeSeatReadProvider getStudyCafeSeatReadProvider() {
        return CONFIG.getStudyCafeSeatReadProvider();
    }

    public static StudyCafeLockerReadProvider getStudyCafeLockerReadProvider() {
        return CONFIG.getStudyCafeLockerReadProvider();
    }

    public static Map<StudyCafePassType, StudyCafePassType> getStrategyMap() {
        return CONFIG.getStrategyMap();
    }

    public static Map<StudyCafePassType, LockerPolicyType> getLockerPolicyMap() {
        return CONFIG.getLockerPolicyMap();
    }
}
