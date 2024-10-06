package me.sungbin.day7;

import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.StudyCafeLockerPass;
import me.sungbin.day7.model.StudyCafePass;
import me.sungbin.day7.model.StudyCafePassType;

import java.util.List;
import java.util.function.Predicate;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private final List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            processPassSelection(studyCafePassType);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void processPassSelection(StudyCafePassType passType) {
        // 필터링 기준을 결정
        Predicate<StudyCafePass> passFilter = pass -> pass.getPassType() == passType;

        List<StudyCafePass> filteredPasses = studyCafePasses.stream()
                .filter(passFilter)
                .toList();

        outputHandler.showPassListForSelection(filteredPasses);
        StudyCafePass selectedPass = inputHandler.getSelectPass(filteredPasses);

        if (passType == StudyCafePassType.FIXED) {
            processFixedPass(selectedPass);
        } else {
            outputHandler.showPassOrderSummary(selectedPass, null);
        }
    }

    private void processFixedPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();

        StudyCafeLockerPass lockerPass = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType() &&
                                option.getDuration() == selectedPass.getDuration())
                .findFirst()
                .orElse(null);

        boolean lockerSelection = false;

        if (lockerPass != null) {
            outputHandler.askLockerPass(lockerPass);
            lockerSelection = inputHandler.getLockerSelection();
        }

        outputHandler.showPassOrderSummary(selectedPass, lockerSelection ? lockerPass : null);
    }
}
