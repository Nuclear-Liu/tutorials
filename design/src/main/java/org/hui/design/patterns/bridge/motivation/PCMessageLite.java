package org.hui.design.patterns.bridge.motivation;

import java.awt.*;
import java.util.logging.Logger;

/**
 * 业务抽象
 * PC精简版
 */
public class PCMessageLite extends PCMessagerBase {
    @Override
    public void login(String userName, String password) {
        super.connect();
        Logger.getGlobal().info("PCMessageLite.login().");
    }
    @Override
    public void sendMessage(String message) {
        super.writeText();
        Logger.getGlobal().info("PCMessageLite.sendMessage().");
    }
    @Override
    public void sendPicture(Image image) {
        super.drawShape();
        Logger.getGlobal().info("PCMessageLite.sendPicture().");
    }
}
