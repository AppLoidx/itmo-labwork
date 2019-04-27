package core.parser;

import core.entities.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Person parser from files
 *
 * @author Arthur Kupriyanov
 */
public class Parser {
    private static Person readPerson(String line){

        int height = 0;

        String classOf      = CSVExpressionReader.readString("class", line);
        String name         = CSVExpressionReader.readString("name", line);
        String imgSource    = CSVExpressionReader.readString("imgSource", line);
        String temp         = CSVExpressionReader.readString("height", line);
        String playable     = CSVExpressionReader.readString("playable", line);

        if (temp!=null && temp.matches("[0-9]+")){
            height = Integer.parseInt(temp);
        }
        if (classOf==null || name==null) return null;

        Person p;
        if ((p=PersonFabric.getPerson(classOf, name, height))==null) return null;

        p.setSpritePath(imgSource); // image path
        p.setPlayRes(playable);     // video resource path

        return p;
    }

    /**
     * Reads persons from file
     * @param file persons data with CSV format
     * @return Persons list
     */
    public static List<Person> readPersons(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<Person> persons = new ArrayList<>();
        while((line=br.readLine())!=null){
            Person person = readPerson(line);
            if (person!=null) persons.add(person);
        }

        return persons;

    }
}
