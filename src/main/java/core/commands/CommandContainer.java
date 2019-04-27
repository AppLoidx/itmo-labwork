package core.commands;

import core.commands.concrete.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
public class CommandContainer {
    private static Set<Command> commands = new HashSet<>();
    static {
        commands.add(new Add());
        commands.add(new AddIfMax());
        commands.add(new AddIfMin());
        commands.add(new Info());
        commands.add(new Remove());
        commands.add(new RemoveLast());
        commands.add(new Show());
    }

    public static Set<Command> getCommands() {
        return new HashSet<>(commands);
    }

//    On runtime command adding method
//
//    public static void addCommand(Command command){
//        commands.add(command);
//    }

    static Command getCommand(String commandName){
        Optional<Command> cmd = commands.stream().filter(c -> c.getName().equals(commandName)).findFirst();
        return cmd.orElse(null);
    }
}
