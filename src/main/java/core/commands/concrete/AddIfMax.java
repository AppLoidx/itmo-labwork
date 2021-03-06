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
import java.util.Comparator;
import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
public class AddIfMax extends Command implements Helpable {
    public AddIfMax(){}

    @Override
    public String getName() {
        return "add-if-max";
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
                    Optional<Person> maxPerson = col.stream().max(Comparator.comparing(Person::getHeight));
                    if (!maxPerson.isPresent()) CollectionEditor.addPerson(col, person);
                    else if (maxPerson.get().getHeight() < person.getHeight()) {
                        CollectionEditor.addPerson(col, person);
                    }
                }
            };
        }catch (JSONException e){
            return JSONContextReader.jsonException(e);

        } catch (FileNotFoundException e) {
            return col -> System.err.println("Файл для валидации json не был найден по пути " + JSONPersonParser.schemaPath);
        } catch (ValidationException e){
            return JSONContextReader.validationException(e);
        }

    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

}
