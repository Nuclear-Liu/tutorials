package org.hui.design.patterns.bridge.motivation;

import java.awt.*;
import java.util.logging.Logger;

public class MobileMessagerLite extends MobileMessagerBase {
    @Override
    public void login(String userName, String password) {
        super.connect();
        Logger.getGlobal().info("MobileMessagerLite.login().");
    }
    @Override
    public void sendMessage(String message) {
        super.writeText();
        Logger.getGlobal().info("MobileMessagerLite.sendMessage().");
    }
    @Override
    public void sendPicture(Image image) {
        super.drawShape();
        Logger.getGlobal().info("MobileMessagerLite.sendPicture().");
    }
}
