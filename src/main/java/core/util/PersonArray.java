package core.util;

import core.entities.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Arthur Kupriyanov
 */
public class PersonArray extends ArrayDeque<Person> {
    private String changedDate;
    {
        changedDate = SimpleDateFormat.getDateTimeInstance().format(new Date());
    }

    public String getChangedDate() {
        return changedDate;
    }

    public void sort(){
        PersonArray deque = new PersonArray();
        List<Person> temp = this.stream().sorted().collect(Collectors.toList());
        this.clear();
        this.addAll(temp);
    }

    public void setChangedDate(){
        this.changedDate = SimpleDateFormat.getDateTimeInstance().format(new Date());
    }
    public void saveTo(File file) throws IOException {
        if (!file.exists()){
            if (!file.mkdirs()){
                System.err.println("Укажите другой путь для сохранения файла, так как " +
                        "заданный путь недопустим для сохранения файла или введите exit, чтобы выйти " +
                        "без сохранения");
                String line;
                if ((line=new Scanner(System.in).nextLine()).equals("exit")){
                    return;
                } else {
                    saveTo(new File(line));
                }
            }
        }
        if (file.isDirectory()) {
            File data = new File(file.getPath() + "/saved-data.csv");
            writeToFile(data);
        } else {
            writeToFile(file);
        }
    }

    private void writeToFile(File file) throws IOException {
        System.out.println(file.getPath());
        if (file.exists()) {
            if (file.canWrite()) {

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    for (Person p : this) bw.write(p.getCSVSerializeData() + "\n");
                }
            } else {
                System.err.println("Файл недопустим для записи. Измените права, либо выберите " +
                        "другой путь для сохранения файла");
                String line;
                if (!(line = new Scanner(System.in).nextLine()).equals("exit")) {
                    saveTo(new File(line));
                }
            }
        } else {
            if (file.createNewFile()) {
                saveTo(file);
            } else {
                System.err.println("Не удалось создать новый файл. Используйте другую директорию");
                String line;
                if (!(line = new Scanner(System.in).nextLine()).equals("exit")) {
                    saveTo(new File(line));
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int c = 0;
        for (Person p : this){
            c = c*31 + p.hashCode();
        }
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PersonArray){
            List<Person> aObj = new ArrayList<>(((PersonArray) obj));
            List<Person> thisObj = new ArrayList<>(this);

            Collections.sort(aObj);
            Collections.sort(thisObj);

            if (aObj.size() == thisObj.size()){
                for (int i = 0; i < aObj.size() - 1; i++){
                    if (!aObj.get(i).equals(thisObj.get(i))){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        return false;
    }
}
