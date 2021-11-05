package org.hui.design.patterns.bridge.pattern;

import java.awt.*;
import java.util.logging.Logger;

/**
 * 业务抽象
 * PC精简版
 */
public class MessagerLite extends Messager {
//    private MessagerImp messagerImp; // PCMessagerBase or MobileMessagerBase
    public MessagerLite(MessagerImp messagerImp) {
        super(messagerImp);
    }
    @Override
    public void login(String userName, String password) {
        messagerImp.connect();
        Logger.getGlobal().info("MessageLite.login().");
    }
    @Override
    public void sendMessage(String message) {
        messagerImp.writeText();
        Logger.getGlobal().info("MessageLite.sendMessage().");
    }
    @Override
    public void sendPicture(Image image) {
        messagerImp.drawShape();
        Logger.getGlobal().info("MessageLite.sendPicture().");
    }
}
