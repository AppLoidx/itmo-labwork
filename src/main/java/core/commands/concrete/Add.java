package core.commands.concrete;

import core.commands.CollectionAction;
import core.commands.Command;
import core.commands.Helpable;
import core.entities.Person;
import core.util.CollectionEditor;
import core.util.JSONPersonParser;
import org.everit.json.schema.ValidationException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

/**
 * @author Arthur Kupriyanov
 */
public class Add extends Command implements Helpable {
    public Add(){}

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public CollectionAction getAction(String context) {
        String json = JSONContextReader.readJSONContext(context);

        try {
            JSONPersonParser.validate(new JSONObject(json));
            Person person = JSONPersonParser.getPerson(json);

            return col -> {
                if (person == null) {
                    System.err.println("Персонаж с таким классом не существует. Проверьте валидность ваших данных.");
                } else {
                    CollectionEditor.addPerson(col, person);
                }
            };
        }catch (JSONException e){
            return JSONContextReader.jsonException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return col -> System.err.println("Файл для валидации json не был найден по пути " + JSONPersonParser.schemaPath);
        } catch (ValidationException e){
            return JSONContextReader.validationException(e);
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

}
