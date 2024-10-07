package me.sungbin.day7;

import me.sungbin.day7.config.provider.StudyCafeConfigProvider;
import me.sungbin.day7.exception.StudyCafeApplicationException;
import me.sungbin.day7.io.handler.InputHandler;
import me.sungbin.day7.io.handler.OutputHandler;
import me.sungbin.day7.io.provider.StudyCafeSeatReadProvider;
import me.sungbin.day7.model.Initializable;
import me.sungbin.day7.model.RunnableMachine;
import me.sungbin.day7.model.order.Order;
import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.StudyCafeSeatPasses;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;
import me.sungbin.day7.model.pass.locker.policy.LockerPolicyType;

import java.util.List;
import java.util.Map;

public class StudyCafePassMachine implements Initializable, RunnableMachine {

    private final OutputHandler outputHandler;
    private final InputHandler inputHandler;
    private final StudyCafeSeatReadProvider studyCafeSeatReadProvider;
    private final Map<StudyCafePassType, LockerPolicyType> lockerPolicyMap;
    private final Map<StudyCafePassType, StudyCafePassType> strategyMap;

    public StudyCafePassMachine() {
        this.outputHandler = StudyCafeConfigProvider.getOutputHandler();
        this.inputHandler = StudyCafeConfigProvider.getInputHandler();
        this.studyCafeSeatReadProvider = StudyCafeConfigProvider.getStudyCafeSeatReadProvider();
        this.lockerPolicyMap = StudyCafeConfigProvider.getLockerPolicyMap();
        this.strategyMap = StudyCafeConfigProvider.getStrategyMap();
    }

    @Override
    public void initialize() {
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
        } catch (StudyCafeApplicationException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePassType getSelectedPassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private StudyCafeSeatPass selectPassForType(StudyCafePassType passType) {
        StudyCafeSeatPasses availablePasses = studyCafeSeatReadProvider.getStudyCafePasses();
        List<StudyCafeSeatPass> filteredPasses = availablePasses.findBy(passType);
        outputHandler.showPassListForSelection(filteredPasses);
        return inputHandler.getSelectPass(filteredPasses);
    }

    private Order createOrder(StudyCafePassType passType, StudyCafeSeatPass selectedPass) {
        LockerPolicyType lockerPolicy = lockerPolicyMap.get(passType);
        StudyCafeLockerRentalPass lockerPass = lockerPolicy.handleLockerUsage(selectedPass).orElse(null);
        return Order.of(selectedPass, lockerPass);
    }

    private void processOrderWithStrategy(StudyCafePassType passType, Order order) {
        strategyMap.get(passType).handlePass(order);
    }
}
