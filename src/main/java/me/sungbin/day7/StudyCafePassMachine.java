package me.sungbin.day7;

import me.sungbin.day7.config.StudyCafeConfig;
import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.model.LockerPolicyType;
import me.sungbin.day7.model.StudyCafePass;
import me.sungbin.day7.model.StudyCafePassType;

import java.util.List;
import java.util.Map;

public class StudyCafePassMachine {

    private final StudyCafeConfig config = new StudyCafeConfig();

    public void run() {
        try {
            Map<StudyCafePassType, StudyCafePassType> strategyMap = config.getStrategyMap();
            Map<StudyCafePassType, LockerPolicyType> lockerPolicyMap = config.getLockerPolicyMap();

            OutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();

            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePassType selectedPassType = getSelectedPassType();
            StudyCafePass selectedPass = handlePassSelection(selectedPassType);

            // 각 패스 타입의 전략 실행 및 라커 정책 반영
            LockerPolicyType lockerPolicy = lockerPolicyMap.get(selectedPassType);
            strategyMap.get(selectedPassType).handlePass(selectedPass, lockerPolicy);

        } catch (AppException e) {
            config.getOutputHandler().showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            config.getOutputHandler().showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePassType getSelectedPassType() {
        config.getOutputHandler().askPassTypeSelection();
        return config.getInputHandler().getPassTypeSelectingUserAction();
    }

    private StudyCafePass handlePassSelection(StudyCafePassType passType) {
        List<StudyCafePass> availablePasses = config.getFileHandler().readStudyCafePasses();
        List<StudyCafePass> filteredPasses = availablePasses.stream()
                .filter(pass -> pass.isSameType(passType))
                .toList();

        config.getOutputHandler().showPassListForSelection(filteredPasses);
        return config.getInputHandler().getSelectPass(filteredPasses);
    }
}
