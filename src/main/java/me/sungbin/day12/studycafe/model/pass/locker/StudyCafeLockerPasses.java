package me.sungbin.day12.studycafe.model.pass.locker;

import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;

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

    public Optional<StudyCafeLockerPass> findLockerPassBy(StudyCafeSeatPass pass) {
        return lockerPasses.stream()
            .filter(pass::isSameDurationType)
            .findFirst();
    }

}
