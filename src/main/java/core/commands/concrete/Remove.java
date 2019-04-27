package core.commands.concrete;


import core.commands.CollectionAction;
import core.commands.Command;
import core.commands.Helpable;


/**
 * @author Arthur Kupriyanov
 */
public class Remove extends Command implements Helpable {
    public Remove(){}

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public CollectionAction getAction(String context) {

        if (context.matches("where\\s*?name\\s*?=.+")){
            String name = context.split("=")[1];
            return deleteByName(name.trim());
        }
        try {
            int width = Integer.parseInt(context);
            return col -> col.removeIf(person -> person.getHeight() == width);
        } catch (NumberFormatException e){
            return col -> System.err.println("Неверный синтаксис команды");
        }


    }

    private CollectionAction deleteByName(String name){
        return col -> col.removeIf(person -> person.getName().equals(name));
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его значению";
    }

}
