package me.sungbin.day7.model.pass;

import me.sungbin.day7.config.StudyCafeConfigProvider;
import me.sungbin.day7.io.OutputHandler;
import me.sungbin.day7.model.order.Order;

import java.util.function.Consumer;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권", StudyCafePassType::handleCommonPassFor),
    WEEKLY("주 단위 이용권", StudyCafePassType::handleCommonPassFor),
    FIXED("1인 고정석", StudyCafePassType::handleCommonPassFor);

    private final String description;
    private final Consumer<Order> passHandler;

    StudyCafePassType(String description, Consumer<Order> passHandler) {
        this.description = description;
        this.passHandler = passHandler;
    }

    public void handlePass(Order order) {
        passHandler.accept(order);
    }

    private static void handleCommonPassFor(Order order) {
        OutputHandler outputHandler = StudyCafeConfigProvider.getOutputHandler();

        outputHandler.showPassOrderSummary(order);
    }
}
