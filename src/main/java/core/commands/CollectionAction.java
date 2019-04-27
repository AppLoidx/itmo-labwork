package core.commands;

import core.util.PersonArray;


/**
 * @author Arthur Kupriyanov
 */
@FunctionalInterface
public interface CollectionAction {
    void action(PersonArray col);
}
