package core.parser;

import core.entities.Person;

/**
 * @author Arthur Kupriyanov
 */
public class PersonFabric {

    /**
     * Создает объект класса указанного в параметре <code>classOf</code>
     * @param classOf класс создаваемого объекта, если он не валиден - возвразает null
     * @param name имя персонажа
     * @param height высота персонажа
     * @return объект персонажа с указанным в аргументе классом
     */
    public static Person getPerson(String classOf, String name, int height){

        for (PersonClass p: PersonClass.values()){

            if (p.toString().equals(classOf.toUpperCase())){
                return p.getInstance(name, height);
            }
        }

        return null;
    }
}
