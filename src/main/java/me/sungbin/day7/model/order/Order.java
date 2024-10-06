package me.sungbin.day7.model.order;

import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;

public class Order {
    private final StudyCafeSeatPass pass;
    private final StudyCafeLockerRentalPass lockerPass;

    private Order(StudyCafeSeatPass pass, StudyCafeLockerRentalPass lockerPass) {
        this.pass = pass;
        this.lockerPass = lockerPass;
    }

    public static Order of(StudyCafeSeatPass pass, StudyCafeLockerRentalPass lockerPass) {
        return new Order(pass, lockerPass);
    }

    public int getDiscountPrice() {
        return pass.getCalculatePrice();
    }

    public int getTotalPrice() {
        return pass.getPrice() - getDiscountPrice() + (lockerPass != null ? lockerPass.getPrice() : 0);
    }

    public StudyCafeSeatPass getPass() {
        return pass;
    }

    public StudyCafeLockerRentalPass getLockerPass() {
        return lockerPass;
    }
}
