package org.hui.design.patterns.command.pattern;

public class ConcreteCommand1 implements Command {
    private String arg;

    public ConcreteCommand1(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute() {
        System.out.println("#1 process...");
    }
}
