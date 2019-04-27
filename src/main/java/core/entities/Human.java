package core.entities;

/**
 * @author Arthur Kupriyanov
 */
public class Human extends Person {
    public Human(String name, int height) {
        super(name, height);
    }

    public String helloMessage() {
        return "Hello!";
    }
}
