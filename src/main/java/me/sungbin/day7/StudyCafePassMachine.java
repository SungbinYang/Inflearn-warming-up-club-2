package me.sungbin.day7;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.io.ConsoleOutputHandler;
import me.sungbin.day7.model.Initializable;
import me.sungbin.day7.model.RunnableMachine;
import me.sungbin.day7.model.order.Order;
import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.StudyCafeSeatPasses;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;
import me.sungbin.day7.model.pass.locker.policy.LockerPolicyType;

import java.util.List;

public class StudyCafePassMachine implements Initializable, RunnableMachine {

    @Override
    public void initialize() {
        ConsoleOutputHandler outputHandler = StudyCafeConfigProvider.getConfig().getOutputHandler();
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();
    }

    @Override
    public void run() {
        try {
            StudyCafePassType selectedPassType = getSelectedPassType();
            StudyCafeSeatPass selectedPass = selectPassForType(selectedPassType);
            Order order = createOrder(selectedPassType, selectedPass);
            processOrderWithStrategy(selectedPassType, order);
        } catch (AppException e) {
            handleAppException(e);
        } catch (Exception e) {
            handleGeneralException(e);
        }
    }

    // 선택된 패스 타입 처리
    private StudyCafePassType getSelectedPassType() {
        StudyCafeConfigProvider.getConfig().getOutputHandler().askPassTypeSelection();
        return StudyCafeConfigProvider.getConfig().getInputHandler().getPassTypeSelectingUserAction();
    }

    // 패스 선택
    private StudyCafeSeatPass selectPassForType(StudyCafePassType passType) {
        StudyCafeSeatPasses availablePasses = StudyCafeConfigProvider.getConfig().getFileHandler().readStudyCafePasses();
        List<StudyCafeSeatPass> filteredPasses = availablePasses.findBy(passType);
        StudyCafeConfigProvider.getConfig().getOutputHandler().showPassListForSelection(filteredPasses);
        return StudyCafeConfigProvider.getConfig().getInputHandler().getSelectPass(filteredPasses);
    }

    // 사물함 정책에 따라 Order 생성
    private Order createOrder(StudyCafePassType passType, StudyCafeSeatPass selectedPass) {
        LockerPolicyType lockerPolicy = StudyCafeConfigProvider.getConfig().getLockerPolicyMap().get(passType);
        StudyCafeLockerRentalPass lockerPass = lockerPolicy.handleLockerUsage(selectedPass).orElse(null);
        return Order.createOrder(selectedPass, lockerPass);
    }

    // 전략 처리
    private void processOrderWithStrategy(StudyCafePassType passType, Order order) {
        StudyCafeConfigProvider.getConfig().getStrategyMap().get(passType).handlePass(order);
    }

    // 예외 처리
    private void handleAppException(AppException e) {
        StudyCafeConfigProvider.getConfig().getOutputHandler().showSimpleMessage(e.getMessage());
    }

    private void handleGeneralException(Exception e) {
        StudyCafeConfigProvider.getConfig().getOutputHandler().showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        System.out.println(e.getMessage());
    }
}
