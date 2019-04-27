package core.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Суперкласс для всех элементов коллекции
 *
 * Переопределены hashCode и equals
 *
 * @author Arthur Kupriyanov
 */
public abstract class Person implements Comparable<Person> {



    private final int height;


    private final String name;
    private final String addedTime;
    {
        addedTime = SimpleDateFormat.getDateTimeInstance().format(new Date());
    }


    public void setPlayRes(String playRes) {
        this.playRes = playRes;
    }

    private String playRes;
    private String spritePath;

    public Person(String name, int height) {
        this.height = height;
        this.name = name;
    }

    public Person(int height, String name, String spritePath) {
        this.height = height;
        this.name = name;
        this.spritePath = spritePath;
    }

    public int compareTo(Person o) {
        return o.height - height;
    }

    public abstract String helloMessage();

    public void setSpritePath(String path){
        this.spritePath = path;
    }

//    public String getSpritePath() {
//        return spritePath;
//    }
//    public String getPlayRes() {
//        return playRes;
//    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public String getCSVSerializeData(){
        return "class="+this.getClass().getSimpleName()
                +", height="+height
                +", name="+name +
                (playRes==null?"":", playable="+ playRes) +
                (spritePath==null?"":", imgSource="+spritePath);
    }

    @Override
    public String toString() {
        return "Person:" +
                "\n\tClass : " + this.getClass().getSimpleName() +
                "\n\tHeight : " + height +
                "\n\tName : '" + name + '\'' +
                "\n\tVideo Resource : '" + (playRes!=null?playRes:"not stated") + '\'' +
                "\n\tImage Resource : '" + (spritePath!=null?spritePath:"not stated") + '\'' +
                "\n\tAdded on : " + addedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return height == person.height &&
                name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, name);
    }
}
