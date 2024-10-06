package me.sungbin.day7.io;

import me.sungbin.day7.model.pass.StudyCafePassType;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPass;
import me.sungbin.day7.model.pass.locker.StudyCafeLockerRentalPasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeLockerPassFileReadProvider implements StudyCafeLockerReadProvider {

    public static final String LOCKER_PATH = "src/main/resources/cleancode/studycafe/locker.csv";

    @Override
    public StudyCafeLockerRentalPasses getLockerPasses(StudyCafePassType passType) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(LOCKER_PATH));
            List<StudyCafeLockerRentalPass> lockerPasses = new ArrayList<>();

            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType filePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);

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
