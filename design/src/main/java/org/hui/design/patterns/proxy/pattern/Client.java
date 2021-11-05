package org.hui.design.patterns.proxy.pattern;

public class Client {
    private Subject subject;

    public Client(Subject subject) {
        this.subject = new SubjectProxy(); // 通过某种工厂方式得到一个 subject
    }

    public void doTask() {
        // ...
        subject.proecess();
        // ...
    }
}
