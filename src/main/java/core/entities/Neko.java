package core.entities;

/**
 * @author Arthur Kupriyanov
 */
public class Neko extends Person {

    public Neko(String name, int height) {
        super(name, height);
    }

    public String helloMessage() {
        return "Nyan nyan nyan ni hao nyan!";
    }
}
