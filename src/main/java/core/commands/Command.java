package core.commands;

/**
 * Superclass for all commands
 *
 * @author Arthur Kupriyanov
 */
public abstract class Command {
    private CollectionAction action;
    private String name;
    public Command(){}
    public Command(String name, CollectionAction action) {
        this.action = action;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public CollectionAction getAction(String context){
        return action;
    }

}
