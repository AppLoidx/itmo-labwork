package core.util;

import core.entities.Person;
import core.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Arthur Kupriyanov
 */
public class CollectionEditor {
    public static void addPerson(PersonArray array, Person p){
            array.add(p);
            System.out.println("Добавлен новый персонаж!");
            System.out.println(p.getName() + ": " + p.helloMessage());
    }

    public static void addPersonsFromCSV(PersonArray array, File file){
        try {
            array.addAll(Parser.readPersons(file));
        } catch (IOException e) {
            System.err.println("Не удалось прочитать файл по пути: " + file.getPath()
                    + "\nОшибка: " + e.getMessage()
                    + "\nВывести stacktrace?[y/n]");
            if (new Scanner(System.in).nextLine().equals("y")) {
                e.printStackTrace();
            }
        }
    }
}
