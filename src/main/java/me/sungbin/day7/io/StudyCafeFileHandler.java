package me.sungbin.day7.io;

import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.StudyCafeSeatPass;
import me.sungbin.day7.model.pass.StudyCafeSeatPasses;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    public StudyCafeSeatPasses readStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv"));
            List<StudyCafeSeatPass> studyCafePasses = new ArrayList<>();

            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);
                double discountRate = Double.parseDouble(values[3]);

                StudyCafeSeatPass studyCafePass = StudyCafeSeatPass.of(studyCafePassType, duration, price, discountRate);
                studyCafePasses.add(studyCafePass);
            }

            return StudyCafeSeatPasses.of(studyCafePasses);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    public StudyCafeLockerRentalPasses readLockerPasses(StudyCafePassType passType) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv"));
            List<StudyCafeLockerRentalPass> lockerPasses = new ArrayList<>();

            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType filePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);  // 기간
                int price = Integer.parseInt(values[2]);     // 가격

                if (passType == filePassType) {
                    StudyCafeLockerRentalPass lockerPass = StudyCafeLockerRentalPass.of(passType, duration, price);
                    lockerPasses.add(lockerPass);
                }

            }

            return StudyCafeLockerRentalPasses.of(lockerPasses);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }
}
