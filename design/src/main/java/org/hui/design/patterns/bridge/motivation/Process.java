package org.hui.design.patterns.bridge.motivation;

public class Process {
    // 编译时装配
    Messager messager = new MobileMessagerPerfect();
}
