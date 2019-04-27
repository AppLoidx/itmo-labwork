package core.commands.concrete;

import core.commands.CollectionAction;
import core.commands.Command;
import core.commands.Helpable;

/**
 * @author Arthur Kupriyanov
 */
public class Show extends Command implements Helpable {
    public Show(){}

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public CollectionAction getAction(String context) {
        return col -> col.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

}
