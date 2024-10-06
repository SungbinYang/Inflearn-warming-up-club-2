package me.sungbin.day7.io.handler;

import me.sungbin.day7.exception.AppException;
import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;

import java.util.List;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = SCANNER.nextLine();

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

    @Override
    public StudyCafeSeatPass getSelectPass(List<StudyCafeSeatPass> passes) {
        try {
            String userInput = SCANNER.nextLine();
            int selectedIndex = Integer.parseInt(userInput) - 1;

            if (selectedIndex < 0 || selectedIndex >= passes.size()) {
                throw new AppException("잘못된 선택입니다.");
            }

            return passes.get(selectedIndex);
        } catch (NumberFormatException e) {
            throw new AppException("잘못된 형식의 입력입니다. 숫자를 입력해 주세요.");
        }
    }

    @Override
    public boolean getLockerSelection() {
        String userInput = SCANNER.nextLine();

        return "1".equals(userInput);
    }
}
