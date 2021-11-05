package org.hui.design.patterns.bridge.pattern;

import java.awt.*;
import java.util.logging.Logger;

public class MessagerPerfect extends Messager {
    //    private MessagerImp messagerImp;
    public MessagerPerfect(MessagerImp messagerImp) {
        super(messagerImp);
    }
    @Override
    public void login(String userName, String password) {
        messagerImp.playSound();
        Logger.getGlobal().info("MessagerPerfect.login().");
        messagerImp.connect();
    }
    @Override
    public void sendMessage(String message) {
        messagerImp.playSound();
        Logger.getGlobal().info("MessagerPerfect.sendMessage().");
        messagerImp.writeText();
    }
    @Override
    public void sendPicture(Image image) {
        messagerImp.playSound();
        Logger.getGlobal().info("MessagerPerfect.sendPicture().");
        messagerImp.drawShape();
    }
}
