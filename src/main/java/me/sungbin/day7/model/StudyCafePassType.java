package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.OutputHandler;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권") {
        @Override
        public void handlePass(StudyCafePass selectedPass, LockerPolicyType lockerPolicy) {
            OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

            // 라커 정책에 따라 라커 사용 여부를 물어본 후 처리
            lockerPolicy.handleLockerUsage(selectedPass).ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );
        }
    },
    WEEKLY("주 단위 이용권") {
        @Override
        public void handlePass(StudyCafePass selectedPass, LockerPolicyType lockerPolicy) {
            OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

            // 라커 정책에 따라 라커 사용 여부를 물어본 후 처리
            lockerPolicy.handleLockerUsage(selectedPass).ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );
        }
    },
    FIXED("1인 고정석") {
        @Override
        public void handlePass(StudyCafePass selectedPass, LockerPolicyType lockerPolicy) {
            OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

            // 라커 정책에 따라 라커 사용 여부를 물어본 후 처리
            lockerPolicy.handleLockerUsage(selectedPass).ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );
        }
    };

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // 라커 정책을 외부에서 주입받아 처리
    public abstract void handlePass(StudyCafePass selectedPass, LockerPolicyType lockerPolicy);
}
