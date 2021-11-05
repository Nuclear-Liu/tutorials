package org.hui.design.patterns.bridge.pattern;

import java.util.logging.Logger;

public class MobileMessagerImp implements MessagerImp {
    @Override
    public void playSound() {
        Logger.getGlobal().info("MobileMessagerBase.playSound().");
    }
    @Override
    public void drawShape() {
        Logger.getGlobal().info("MobileMessagerBase.drawShape().");
    }
    @Override
    public void writeText() {
        Logger.getGlobal().info("MobileMessagerBase.writeText().");
    }
    @Override
    public void connect() {
        Logger.getGlobal().info("MobileMessagerBase.connect().");
    }
}
