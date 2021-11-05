package org.hui.design.patterns.builder.pattern;

import java.util.logging.Logger;

public class StoneHouseBuilder extends HouseBuilder {
    public StoneHouseBuilder(House house) {
        super(house);
    }
    @Override
    protected void buildPart1() {
        Logger.getGlobal().info("StoneHouse.buildPart1()");
    }
    @Override
    protected void buildPart2() {
        Logger.getGlobal().info("StoneHouse.buildPart2()");
    }
    @Override
    protected boolean buildPart3() {
        Logger.getGlobal().info("StoneHouse.buildPart3()");
        return false;
    }
    @Override
    protected void buildPart4() {
        Logger.getGlobal().info("StoneHouse.buildPart4()");
    }
    @Override
    protected void buildPart5() {
        Logger.getGlobal().info("StoneHouse.buildPart5()");
    }
}
