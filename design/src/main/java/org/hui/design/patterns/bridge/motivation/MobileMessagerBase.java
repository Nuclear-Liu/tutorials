package org.hui.design.patterns.bridge.motivation;

import java.util.logging.Logger;

public abstract class MobileMessagerBase implements Messager {
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
