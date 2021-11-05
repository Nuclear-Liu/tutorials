package org.hui.design.patterns.command.pattern;

public class Main {
    public static void main(String[] args) {
        Command command1 = new ConcreteCommand1("Arg ###");
        Command command2 = new ConcreteCommand2("Arg $$$");

        MacroCommand macro = new MacroCommand();
        macro.addCommand(command1);
        macro.addCommand(command2);

        macro.execute();
    }
}
