package org.hui.design.patterns.builder.pattern;

public class HouseDirector {
    private HouseBuilder builder;
    public HouseDirector(HouseBuilder builder) {
        this.builder = builder;
    }
    public House construct() {
        builder.buildPart1();
        for (int i = 0; i < 4; i++) {
            builder.buildPart2();
        }
        boolean flag = builder.buildPart3();
        if (flag) {
            builder.buildPart4();
        }
        builder.buildPart5();
        return builder.getResult();
    }
}
