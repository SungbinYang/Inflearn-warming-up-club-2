package me.sungbin.day7.io.handler;

import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;

import java.util.List;

public interface InputHandler {

    StudyCafePassType getPassTypeSelectingUserAction();

    StudyCafeSeatPass getSelectPass(List<StudyCafeSeatPass> passes);

    boolean getLockerSelection();
}
