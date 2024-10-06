package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.StudyCafeFileHandler;

import java.util.Optional;

public enum LockerPolicyType {

    ALLOWED {
        @Override
        public boolean canUseLocker() {
            return true;
        }

        @Override
        public Optional<StudyCafeLockerPass> handleLockerUsage(StudyCafePass selectedPass) {
            StudyCafeFileHandler fileHandler = StudyCafeConfigProvider.getConfig().getFileHandler();

            StudyCafeLockerPasses availableLockerPasses = fileHandler.readLockerPasses(selectedPass.getPassType());

            return availableLockerPasses.findBy(selectedPass);
        }
    },
    NOT_ALLOWED {
        @Override
        public boolean canUseLocker() {
            return false;
        }

        @Override
        public Optional<StudyCafeLockerPass> handleLockerUsage(StudyCafePass selectedPass) {
            return Optional.empty();
        }
    };

    public abstract boolean canUseLocker();
    public abstract Optional<StudyCafeLockerPass> handleLockerUsage(StudyCafePass selectedPass);
}

