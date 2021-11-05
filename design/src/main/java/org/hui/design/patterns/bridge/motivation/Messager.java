package org.hui.design.patterns.bridge.motivation;

import java.awt.*;

public interface Messager {
    void login(String userName, String password);
    void sendMessage(String message);
    void sendPicture(Image image);

    void playSound();
    void drawShape();
    void writeText();
    void connect();
}
