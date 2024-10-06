package me.sungbin.day7.io;

import me.sungbin.day7.model.StudyCafeLockerPass;
import me.sungbin.day7.model.StudyCafePass;
import me.sungbin.day7.model.StudyCafePassType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    public List<StudyCafePass> readStudyCafePasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv"));
            List<StudyCafePass> studyCafePasses = new ArrayList<>();

            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);
                double discountRate = Double.parseDouble(values[3]);

                StudyCafePass studyCafePass = StudyCafePass.of(studyCafePassType, duration, price, discountRate);
                studyCafePasses.add(studyCafePass);
            }

            return studyCafePasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    public List<StudyCafeLockerPass> readLockerPasses(StudyCafePassType passType) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv"));
            List<StudyCafeLockerPass> lockerPasses = new ArrayList<>();

            for (String line : lines) {
                String[] values = line.split(",");
                int duration = Integer.parseInt(values[0]);  // 기간
                int price = Integer.parseInt(values[1]);     // 가격

                // 현재 라커 정책에 맞는 라커 패스 생성
                StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, price);
                lockerPasses.add(lockerPass);
            }

            return lockerPasses;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }
}
