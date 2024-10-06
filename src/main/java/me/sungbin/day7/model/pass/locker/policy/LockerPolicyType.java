package me.sungbin.day7.model.pass.locker.policy;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.StudyCafeFileHandler;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPasses;

import java.util.Optional;

public enum LockerPolicyType {

    ALLOWED {
        @Override
        public boolean canUseLocker() {
            return true;
        }

        @Override
        public Optional<StudyCafeLockerRentalPass> handleLockerUsage(StudyCafeSeatPass selectedPass) {
            StudyCafeFileHandler fileHandler = StudyCafeConfigProvider.getConfig().getFileHandler();

            StudyCafeLockerRentalPasses availableLockerPasses = fileHandler.readLockerPasses(selectedPass.getPassType());

            return availableLockerPasses.findBy(selectedPass);
        }
    },
    NOT_ALLOWED {
        @Override
        public boolean canUseLocker() {
            return false;
        }

        @Override
        public Optional<StudyCafeLockerRentalPass> handleLockerUsage(StudyCafeSeatPass selectedPass) {
            return Optional.empty();
        }
    };

    public abstract boolean canUseLocker();
    public abstract Optional<StudyCafeLockerRentalPass> handleLockerUsage(StudyCafeSeatPass selectedPass);
}

