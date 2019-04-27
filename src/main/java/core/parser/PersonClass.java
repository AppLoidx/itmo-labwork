package core.parser;

import core.entities.Human;
import core.entities.Neko;
import core.entities.Person;
import core.entities.Tanuki;

/**
 * @author Arthur Kupriyanov
 */
public enum PersonClass {

    HUMAN{
        @Override
        public Person getInstance(String name, int height) {
            return new Human(name, height);
        }
    },

    NEKO {
        @Override
        public Person getInstance(String name, int height) {
            return new Neko(name, height);
        }
    },

    TANUKI {
        @Override
        public Person getInstance(String name, int height) {
            return new Tanuki(name, height);
        }
    };

    public abstract Person getInstance(String name, int height);
}
