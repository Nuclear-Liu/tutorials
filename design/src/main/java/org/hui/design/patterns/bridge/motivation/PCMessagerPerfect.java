package org.hui.design.patterns.bridge.motivation;

import java.awt.*;
import java.util.logging.Logger;

public class PCMessagerPerfect extends PCMessagerBase {
    @Override
    public void login(String userName, String password) {
        super.playSound();
        Logger.getGlobal().info("PCMessagerPerfect.login().");
        super.connect();
    }
    @Override
    public void sendMessage(String message) {
        super.playSound();
        Logger.getGlobal().info("PCMessagerPerfect.sendMessage().");
        super.writeText();
    }
    @Override
    public void sendPicture(Image image) {
        super.playSound();
        Logger.getGlobal().info("PCMessagerPerfect.sendPicture().");
        super.drawShape();
    }
}
