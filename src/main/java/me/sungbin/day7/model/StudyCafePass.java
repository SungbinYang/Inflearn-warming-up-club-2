package me.sungbin.day7.model;

public class StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public boolean isSameType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSameLocker(StudyCafeLockerPass lockerPass) {
        return lockerPass.isSameType(this.passType)
                && lockerPass.isSameDuration(this.duration);
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public String display() {
        return switch (passType) {
            case HOURLY -> String.format("%s시간권 - %d원", duration, price);
            case WEEKLY -> String.format("%s주권 - %d원", duration, price);
            case FIXED -> String.format("%s주권 - %d원", duration, price);
        };
    }
}
