package me.sungbin.day12.studycafe;

import me.sungbin.day12.studycafe.io.provider.LockerPassFileReader;
import me.sungbin.day12.studycafe.io.provider.SeatPassFileReader;
import me.sungbin.day12.studycafe.provider.LockerPassProvider;
import me.sungbin.day12.studycafe.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
            seatPassProvider, lockerPassProvider
        );
        studyCafePassMachine.run();
    }

}
