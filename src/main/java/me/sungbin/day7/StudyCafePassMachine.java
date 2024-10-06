package me.sungbin.day7;

import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.*;

import java.util.List;
import java.util.Map;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

    private final Map<StudyCafePassType, StudyCafePassStrategy> strategyMap = Map.of(
            StudyCafePassType.FIXED, new FixedPassStrategy(inputHandler, outputHandler, fileHandler),
            StudyCafePassType.HOURLY, new HourlyPassStrategy(outputHandler),
            StudyCafePassType.WEEKLY, new WeeklyPassStrategy(outputHandler)
    );

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePassType selectedPassType = getSelectedPassType();
            StudyCafePass selectedPass = handlePassSelection(selectedPassType);

            StudyCafePassStrategy strategy = strategyMap.get(selectedPassType);
            strategy.handlePass(selectedPass);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePassType getSelectedPassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private StudyCafePass handlePassSelection(StudyCafePassType passType) {
        List<StudyCafePass> availablePasses = fileHandler.readStudyCafePasses();
        List<StudyCafePass> filteredPasses = availablePasses.stream()
                .filter(pass -> pass.getPassType() == passType)
                .toList();

        outputHandler.showPassListForSelection(filteredPasses);
        return inputHandler.getSelectPass(filteredPasses);
    }
}