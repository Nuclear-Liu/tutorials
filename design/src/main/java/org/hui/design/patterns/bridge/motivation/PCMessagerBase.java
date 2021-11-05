package org.hui.design.patterns.bridge.motivation;

import java.util.logging.Logger;

public abstract class PCMessagerBase implements Messager {
    @Override
    public void playSound() {
        Logger.getGlobal().info("PcMessagerBase.playSound().");
    }
    @Override
    public void drawShape() {
        Logger.getGlobal().info("PcMessagerBase.drawShape().");
    }
    @Override
    public void writeText() {
        Logger.getGlobal().info("PcMessagerBase.writeText().");
    }
    @Override
    public void connect() {
        Logger.getGlobal().info("PcMessagerBase.connect().");
    }
}
