package org.hui.design.patterns.builder.motivation;

import org.hui.design.patterns.builder.pattern.House;

public abstract class HouseBuilder {
    private House house;
    public void init() {
        buildPart1();
        for (int i = 0; i < 4; i++) {
            buildPart2();
        }
        boolean flag = buildPart3();
        if (flag) {
            buildPart4();
        }
        buildPart5();
    }
    public House getResult() {
        return house;
    }
    protected abstract void buildPart1();
    protected abstract void buildPart2();
    protected abstract boolean buildPart3();
    protected abstract void buildPart4();
    protected abstract void buildPart5();
}
