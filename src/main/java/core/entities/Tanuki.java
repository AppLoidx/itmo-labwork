package core.entities;

/**
 * @author Arthur Kupriyanov
 */
public class Tanuki extends Person {
    public Tanuki(String name, int height) {
        super(name, height);
    }

    @Override
    public String helloMessage() {
        return "Hey, hello!";
    }
}
