package me.sungbin.day7.model;

import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;

import java.util.List;
import java.util.Optional;

public class FixedPassStrategy implements StudyCafePassStrategy {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final StudyCafeFileHandler fileHandler;

    public FixedPassStrategy(InputHandler inputHandler, OutputHandler outputHandler, StudyCafeFileHandler fileHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.fileHandler = fileHandler;
    }

    @Override
    public void handlePass(StudyCafePass selectedPass) {
        findLockerForFixedPass(selectedPass)
                .ifPresentOrElse(
                        lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                        () -> outputHandler.showPassOrderSummary(selectedPass)
                );
    }

    private Optional<StudyCafeLockerPass> findLockerForFixedPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> availableLockerPasses = fileHandler.readLockerPasses();

        return availableLockerPasses.stream()
                .filter(lockerPass -> lockerPass.getPassType() == selectedPass.getPassType() &&
                        lockerPass.getDuration() == selectedPass.getDuration())
                .findFirst()
                .flatMap(lockerPass -> {
                    outputHandler.askLockerPass(lockerPass);
                    boolean lockerSelected = inputHandler.getLockerSelection();
                    return lockerSelected ? Optional.of(lockerPass) : Optional.empty();
                });
    }
}
