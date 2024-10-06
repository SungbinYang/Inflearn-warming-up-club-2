package me.sungbin.day7;

import me.sungbin.day7.config.StudyCafeConfig;
import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.*;

import java.util.List;
import java.util.Map;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

    private final Map<StudyCafePassType, StudyCafePassStrategy> strategyMap;

    public StudyCafePassMachine(StudyCafeConfig studyCafeConfig) {
        this.inputHandler = studyCafeConfig.getInputHandler();
        this.outputHandler = studyCafeConfig.getOutputHandler();
        this.strategyMap = studyCafeConfig.getStrategyMap();
    }

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