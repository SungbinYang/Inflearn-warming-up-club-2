package me.sungbin.day12.studycafe.io;

import me.sungbin.day12.studycafe.exception.AppException;
import me.sungbin.day12.studycafe.model.pass.StudyCafePassType;
import me.sungbin.day12.studycafe.model.pass.StudyCafeSeatPass;

import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = scanner.nextLine();

        if ("1".equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if ("2".equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if ("3".equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

    public StudyCafeSeatPass getSelectPass(List<StudyCafeSeatPass> passes) {
        String userInput = scanner.nextLine();
        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public boolean getLockerSelection() {
        String userInput = scanner.nextLine();

        if ("1".equals(userInput)) {
            return true;
        }

        if ("2".equals(userInput)) {
            return false;
        }

        throw new AppException("잘못된 입력입니다.");
    }
}
