package me.sungbin.day7.model;

import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;

import java.util.List;

public class FixedPassStrategy implements StudyCafePassStrategy {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final StudyCafeFileHandler fileHandler;
    private final StudyCafePassHelper passHelper;

    public FixedPassStrategy(InputHandler inputHandler, OutputHandler outputHandler, StudyCafeFileHandler fileHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.fileHandler = fileHandler;
        this.passHelper = new StudyCafePassHelper(outputHandler);
    }

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        // 고정석 처리: 사물함 선택
        StudyCafeLockerPass lockerPass = findLockerForFixedPass(selectedPass);

        // 사물함 선택 여부에 따라 결제 내역 표시
        passHelper.showPassOrderSummary(selectedPass, lockerPass);
    }

    // 고정석 전용 메서드: 고정석 관련 기능은 여기서만 처리
    private StudyCafeLockerPass findLockerForFixedPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> availableLockerPasses = fileHandler.readLockerPasses();

        return availableLockerPasses.stream()
                .filter(lockerPass -> lockerPass.getPassType() == selectedPass.getPassType() &&
                        lockerPass.getDuration() == selectedPass.getDuration())
                .findFirst()
                .map(lockerPass -> {
                    outputHandler.askLockerPass(lockerPass);
                    return inputHandler.getLockerSelection() ? lockerPass : null;
                })
                .orElse(null);
    }
}
