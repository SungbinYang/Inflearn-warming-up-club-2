package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;

import java.util.List;
import java.util.Optional;

public class FixedPassStrategy implements StudyCafePassStrategy {

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

        findLockerForFixedPass(selectedPass)
                .ifPresentOrElse(
                        lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                        () -> outputHandler.showPassOrderSummary(selectedPass)
                );
    }

    private Optional<StudyCafeLockerPass> findLockerForFixedPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> availableLockerPasses = StudyCafeConfigProvider.getConfig().getFileHandler().readLockerPasses();
        InputHandler inputHandler = StudyCafeConfigProvider.getConfig().getInputHandler();
        OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

        return availableLockerPasses.stream()
                .filter(selectedPass::isSameLocker)
                .findFirst()
                .flatMap(lockerPass -> {
                    outputHandler.askLockerPass(lockerPass);
                    boolean lockerSelected = inputHandler.getLockerSelection();
                    return lockerSelected ? Optional.of(lockerPass) : Optional.empty();
                });
    }
}
