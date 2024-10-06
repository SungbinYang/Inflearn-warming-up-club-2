package me.sungbin.day7.model.pass.locker;

import me.sungbin.day7.config.provider.StudyCafeConfigProvider;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerRentalPasses {

    private final List<StudyCafeLockerRentalPass> lockerPasses;

    private StudyCafeLockerRentalPasses(List<StudyCafeLockerRentalPass> lockerPasses) {
        this.lockerPasses = lockerPasses;
    }

    public static StudyCafeLockerRentalPasses of(List<StudyCafeLockerRentalPass> lockerPasses) {
        return new StudyCafeLockerRentalPasses(lockerPasses);
    }

    public Optional<StudyCafeLockerRentalPass> findBy(StudyCafeSeatPass selectedPass) {
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
