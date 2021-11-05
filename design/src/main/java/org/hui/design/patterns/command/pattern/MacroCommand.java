package org.hui.design.patterns.command.pattern;

import java.util.ArrayList;
import java.util.List;

public class MacroCommand implements Command {
    List<Command> commands = new ArrayList<>();
    public void addCommand(Command command) {
        this.commands.add(command);
    }
    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
}
