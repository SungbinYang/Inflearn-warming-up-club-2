package me.sungbin.day7.io.provider;

import me.sungbin.day7.model.pass.StudyCafeSeatPasses;

@FunctionalInterface
public interface StudyCafeSeatReadProvider {
    StudyCafeSeatPasses getStudyCafePasses();
}
