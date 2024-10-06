package me.sungbin.day7.model.pass;

import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;

public class StudyCafeSeatPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafeSeatPass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafeSeatPass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafeSeatPass(passType, duration, price, discountRate);
    }

    public boolean isSameType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSameLocker(StudyCafeLockerRentalPass lockerPass) {
        return lockerPass.isSameType(this.passType)
                && lockerPass.isSameDuration(this.duration);
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
