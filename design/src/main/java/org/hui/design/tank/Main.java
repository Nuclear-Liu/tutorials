package org.hui.design.tank;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        // 初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            tankFrame.tanks.add(new Tank(50 + i * 30, 200, Dir.DOWN, Group.BAD, tankFrame));
        }
        while (true) {
            TimeUnit.MILLISECONDS.sleep(50);
            tankFrame.repaint();
        }
    }
}
