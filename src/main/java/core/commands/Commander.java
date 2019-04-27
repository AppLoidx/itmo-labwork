package core.commands;

import core.util.PersonArray;

/**
 * @author Arthur Kupriyanov
 */
public class Commander {
    private PersonArray col;
    public Commander(PersonArray col){
        this.col = col;
    }

    /**
     * Выполнение команды
     * @param command полный введенный текст команды
     */
    public void execute(String command){
        if (command.isEmpty()) return;
        String[] commandParts = command.split(" ", 2);
        final String COMMAND = commandParts[0];
        final String CONTEXT = commandParts.length > 1? commandParts[1]:"";

        Command cmd = CommandContainer.getCommand(COMMAND);
        if (cmd == null) {
            System.err.println("Команда с именем " + COMMAND + " не найдена");
        } else {
            cmd.getAction(CONTEXT).action(col);
        }
    }
}
