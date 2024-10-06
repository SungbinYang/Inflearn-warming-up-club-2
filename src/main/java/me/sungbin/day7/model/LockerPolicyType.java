package me.sungbin.day7.model;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.InputHandler;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.io.StudyCafeFileHandler;

import java.util.List;
import java.util.Optional;

public enum LockerPolicyType {

    ALLOWED {
        @Override
        public boolean canUseLocker() {
            return true;
        }

        @Override
        public Optional<StudyCafeLockerPass> handleLockerUsage(StudyCafePass selectedPass) {
            OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();
            InputHandler inputHandler = StudyCafeConfigProvider.getConfig().getInputHandler();
            StudyCafeFileHandler fileHandler = StudyCafeConfigProvider.getConfig().getFileHandler();

            List<StudyCafeLockerPass> availableLockerPasses = fileHandler.readLockerPasses(selectedPass.getPassType());

            return availableLockerPasses.stream()
                    .filter(selectedPass::isSameLocker)
                    .findFirst()
                    .flatMap(lockerPass -> {
                        // 라커 사용 여부 묻기
                        outputHandler.askLockerPass(lockerPass);

                        boolean lockerSelected = inputHandler.getLockerSelection();

                        return lockerSelected ? Optional.of(lockerPass) : Optional.empty();
                    });
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

