package core.util;

import com.google.gson.GsonBuilder;
import core.entities.Person;
import core.parser.PersonFabric;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * @author Arthur Kupriyanov
 */
public class JSONPersonParser {
    public static final String schemaPath = "src\\main\\resources\\resources\\json-schemas\\person-validate-schema.json";
    public static void validate(JSONObject json) throws FileNotFoundException, ValidationException {

        File file = new File("src\\main\\resources\\json-schemas\\person-validate-schema.json");
        if (!file.exists()){
            throw new FileNotFoundException("Файл " + file.getPath() + " не найден!");
        }
        Schema schema = SchemaLoader.load(new JSONObject(new JSONTokener(new FileReader(file))));
        schema.validate(json);
    }

    public static Person getPerson(String json){
        PersonData data = new GsonBuilder().create().fromJson(json, PersonData.class);
        Person p = PersonFabric.getPerson(data.getClassOf(), data.getName(), data.getHeight());

        if (p==null) return null;

        p.setSpritePath(data.getImg());
        p.setPlayRes(data.getVideo());

        return p;
    }
}
