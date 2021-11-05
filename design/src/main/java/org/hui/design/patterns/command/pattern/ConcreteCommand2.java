package org.hui.design.patterns.command.pattern;

public class ConcreteCommand2 implements Command {
    private String arg;

    public ConcreteCommand2(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute() {
        System.out.println("#2 process...");
    }
}
