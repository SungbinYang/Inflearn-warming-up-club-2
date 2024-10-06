package me.sungbin.day7.model;

import java.util.List;

public class StudyCafePasses {

    private final List<StudyCafePass> passes;

    private StudyCafePasses(List<StudyCafePass> passes) {
        this.passes = passes;
    }

    public static StudyCafePasses of(List<StudyCafePass> passes) {
        return new StudyCafePasses(passes);
    }

    public List<StudyCafePass> findBy(StudyCafePassType passType) {
        return passes.stream()
                .filter(pass -> pass.isSameType(passType))
                .toList();
    }
}
