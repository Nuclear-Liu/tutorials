package org.hui.design.patterns.memento.pattern;

public class Main {
    public static void main(String[] args) {
        Originator originator = new Originator();
        // 捕获对象状态，存储到备忘录
        Memento memento = originator.createMemento();
        // 改变 originator 状态
        // 从备忘录中恢复
        originator.setMomento(memento);
    }
}
