package me.sungbin.day7.io.provider;

import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPasses;

@FunctionalInterface
public interface StudyCafeLockerReadProvider {
    StudyCafeLockerRentalPasses getLockerPasses(StudyCafePassType passType);
}
