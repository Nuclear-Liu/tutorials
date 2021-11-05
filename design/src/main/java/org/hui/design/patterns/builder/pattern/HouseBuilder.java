package org.hui.design.patterns.builder.pattern;

public abstract class HouseBuilder {
    protected House house;
    public HouseBuilder(House house) {
        this.house = house;
    }
    protected abstract void buildPart1();
    protected abstract void buildPart2();
    protected abstract boolean buildPart3();
    protected abstract void buildPart4();
    protected abstract void buildPart5();
    public House getResult() {
        return house;
    }
}
