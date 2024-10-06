package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerPasses {

    private final List<StudyCafeLockerPass> lockerPasses;

    private StudyCafeLockerPasses(List<StudyCafeLockerPass> lockerPasses) {
        this.lockerPasses = lockerPasses;
    }

    public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> lockerPasses) {
        return new StudyCafeLockerPasses(lockerPasses);
    }

    public Optional<StudyCafeLockerPass> findBy(StudyCafePass selectedPass) {
        return lockerPasses.stream()
                .filter(selectedPass::isSameLocker)
                .findFirst()
                .flatMap(lockerPass -> {
                    StudyCafeConfigProvider.getConfig().getOutputHandler().askLockerPass(lockerPass);

                    boolean lockerSelected = StudyCafeConfigProvider.getConfig().getInputHandler().getLockerSelection();

                    return lockerSelected ? Optional.of(lockerPass) : Optional.empty();
                });
    }
}
