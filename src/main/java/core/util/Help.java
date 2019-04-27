package core.util;

import core.commands.Command;
import core.commands.CommandContainer;
import core.commands.Helpable;

import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
public class Help {
    public static void printHelp(){
        for (Command c : CommandContainer.getCommands()) {
            if (c instanceof Helpable) {
                StringBuilder name = Optional.ofNullable(c.getName()).map(StringBuilder::new).orElse(null);
                if (name != null) {
                    while (name.length() < 15) name.append(" ");
                    System.out.println(String.format("%s - %s", name.toString(), ((Helpable) c).getDescription()));
                }
            } else {
                System.out.println(c.getName() + " - не имеет описания");
            }
        }
    }
}
