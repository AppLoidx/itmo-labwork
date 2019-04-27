package core.commands.concrete;

import core.commands.CollectionAction;
import core.commands.Command;
import core.commands.Helpable;
import core.entities.Person;

/**
 * @author Arthur Kupriyanov
 */
public class Info extends Command implements Helpable {
    public Info(){}

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public CollectionAction getAction(String context) {
        return col -> {
            System.out.println("Дата изменения: " + col.getChangedDate());
            System.out.println("Хэш код: " + col.hashCode());
            StringBuilder sb = new StringBuilder();
            for (Person p : col){
                sb.append(p.getName()).append("[").append(p.getClass().getSimpleName()).append("]").append(", ");
            }

            System.out.println("Участники коллекции:\n " +
                    (sb.toString().isEmpty()?"Объектов еще нет":sb.toString()));
        };

    }


    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

}
