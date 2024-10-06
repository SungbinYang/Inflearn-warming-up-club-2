package me.sungbin.day7.config;

public class StudyCafeConfigProvider {
    private static final StudyCafeConfig CONFIG = new StudyCafeConfig();

    public static StudyCafeConfig getConfig() {
        return CONFIG;
    }
}
