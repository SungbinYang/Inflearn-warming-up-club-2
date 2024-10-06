package me.sungbin.day7.model;

import me.sungbin.day7.io.OutputHandler;

public class StudyCafePassHelper {

    private final OutputHandler outputHandler;

    public StudyCafePassHelper(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    // 공통적으로 사용하는 결제 내역 표시
    public void showPassOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
    }
}
