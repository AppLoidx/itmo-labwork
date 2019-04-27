import core.commands.Commander;
import core.util.CollectionEditor;
import core.util.Help;
import core.util.PersonArray;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Arthur Kupriyanov
 */
public class Application {

    private  final String path2CSV ;
    {
        String path = System.getenv("LAB_INPUT_PATH");
        if (path == null)
        path2CSV = "src\\main\\resources\\data.csv";
        else path2CSV = path;
    }
    private  int lastHashcode;

    public static void main(String[] args) {
        Application app = new Application();
        app.launch();
    }

    private void launch() {
        PersonArray col = new PersonArray();

        if (path2CSV!=null)
        CollectionEditor.addPersonsFromCSV(col, new File(path2CSV));    // data from file

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                col.saveTo(new File("data/saved-data.csv"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        Scanner sc = new Scanner(System.in);
        Commander commander = new Commander(col);

        String line;
        lastHashcode = col.hashCode();
        while(!(line=sc.nextLine()).equals("exit")){
            if (line.trim().equals("help")){
                Help.printHelp();
            }
            commander.execute(line);
            if (col.hashCode() != lastHashcode){
                col.setChangedDate();
            }

        }
    }
}
