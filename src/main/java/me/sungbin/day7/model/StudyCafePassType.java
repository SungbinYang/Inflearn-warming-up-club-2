package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.OutputHandler;

import java.util.function.BiConsumer;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권", StudyCafePassType::handleCommonPassFor),
    WEEKLY("주 단위 이용권", StudyCafePassType::handleCommonPassFor),
    FIXED("1인 고정석", StudyCafePassType::handleCommonPassFor);

    private final String description;
    private final BiConsumer<StudyCafePass, LockerPolicyType> passHandler;

    StudyCafePassType(String description, BiConsumer<StudyCafePass, LockerPolicyType> passHandler) {
        this.description = description;
        this.passHandler = passHandler;
    }

    public String getDescription() {
        return description;
    }

    public void handlePass(StudyCafePass selectedPass, LockerPolicyType lockerPolicy) {
        passHandler.accept(selectedPass, lockerPolicy);
    }

    private static void handleCommonPassFor(StudyCafePass selectedPass, LockerPolicyType lockerPolicy) {
        OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

        lockerPolicy.handleLockerUsage(selectedPass).ifPresentOrElse(
                lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                () -> outputHandler.showPassOrderSummary(selectedPass)
        );
    }
}
