package org.hui.design.patterns.proxy.motivation;

public class Client {
    private Subject subject;

    public Client(Subject subject) {
        this.subject = new RealSubject(); // 通过某种工厂方式得到一个 subject
    }

    public void doTask() {
        // ...
        subject.proecess();
        // ...
    }
}
