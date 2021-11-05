package org.hui.design.patterns.bridge.pattern;

import java.awt.*;

public abstract class Messager {
    protected MessagerImp messagerImp;
    public Messager(MessagerImp messagerImp) {
        this.messagerImp = messagerImp;
    }
    abstract void login(String userName, String password);
    abstract void sendMessage(String message);
    abstract void sendPicture(Image image);
}
