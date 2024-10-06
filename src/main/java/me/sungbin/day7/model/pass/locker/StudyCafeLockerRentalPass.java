package me.sungbin.day7.model.pass.locker;

import me.sungbin.day7.model.pass.StudyCafePass;
import me.sungbin.day7.model.pass.StudyCafePassType;

public class StudyCafeLockerRentalPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerRentalPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerRentalPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerRentalPass(passType, duration, price);
    }

    public boolean isSameType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSameDuration(int duration) {
        return this.duration == duration;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }
}
