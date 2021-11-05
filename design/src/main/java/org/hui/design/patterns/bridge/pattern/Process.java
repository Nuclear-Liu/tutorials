package org.hui.design.patterns.bridge.pattern;

public class Process {
    public static void main(String[] args) {
        // 运行时装配
        MessagerImp messagerImp = new PCMessagerImp();
        Messager messager = new MessagerLite(messagerImp);
    }
}
