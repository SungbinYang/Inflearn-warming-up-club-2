package me.sungbin.day7.io;

import me.sungbin.day7.model.order.Order;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;

import java.util.List;

public interface OutputHandler {

    void showWelcomeMessage();

    void showAnnouncement();

    void askPassTypeSelection();

    void showPassListForSelection(List<StudyCafeSeatPass> passes);

    void askLockerPass(StudyCafeLockerRentalPass lockerPass);

    void showPassOrderSummary(Order order);

    void showSimpleMessage(String message);
}
