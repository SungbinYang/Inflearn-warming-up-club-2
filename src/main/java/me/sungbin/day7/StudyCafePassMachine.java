package me.sungbin.day7;

import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.StudyCafeLockerPass;
import me.sungbin.day7.model.StudyCafePass;
import me.sungbin.day7.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                processHourlyPassSelection();
            } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
                processWeeklyPassSelection();
            } else if (studyCafePassType == StudyCafePassType.FIXED) {
                processFixedPassSelection();
            }

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void processHourlyPassSelection() {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.HOURLY)
                .toList();

        outputHandler.showPassListForSelection(hourlyPasses);
        StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);

        outputHandler.showPassOrderSummary(selectedPass, null);
    }

    private void processWeeklyPassSelection() {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        List<StudyCafePass> weeklyPasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.WEEKLY)
                .toList();

        outputHandler.showPassListForSelection(weeklyPasses);
        StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);

        outputHandler.showPassOrderSummary(selectedPass, null);
    }

    private void processFixedPassSelection() {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        List<StudyCafePass> fixedPasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.FIXED)
                .toList();

        outputHandler.showPassListForSelection(fixedPasses);
        StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

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
