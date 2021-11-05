package org.hui.design.patterns.builder.pattern;

public class Main {
    public static void main(String[] args) {
        House house = new StoneHouse();
        HouseBuilder houseBuilder = new StoneHouseBuilder(house);
        HouseDirector houseDirector = new HouseDirector(houseBuilder);
        House construct = houseDirector.construct();
    }
}
