package core.commands.concrete;

import core.commands.CollectionAction;
import core.commands.Command;
import core.commands.Helpable;

/**
 * @author Arthur Kupriyanov
 */
public class RemoveLast extends Command implements Helpable {
    public RemoveLast(){}

    @Override
    public String getName() {
        return "remove-last";
    }

    @Override
    public CollectionAction getAction(String context) {
        return (col) -> {
            if (col.isEmpty()){
                System.err.println("Collection is empty");
                return;
            }
            col.removeLast();
        };
    }

    @Override
    public String getDescription() {
        return "удалить последний элемент из коллекции";
    }

}
