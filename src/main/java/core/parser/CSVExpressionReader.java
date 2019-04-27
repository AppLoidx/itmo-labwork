package core.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Парсер строки CSV для значений:
 *
 * <blockquote>
 *     .., ключ=значение, ...
 * </blockquote>
 *
 * Работает со строкой в формате CSV
 *
 * @author Arthur Kupriyanov
 */
class CSVExpressionReader {
    static String readString(String field, String data){

        // ленивый квантор .+?
        String regex = ",*?[ ]*" + field + "=.*?(,+|$)";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        if (m.find()) return m.group().split("=")[1].replace(",", "");

        return null;

    }
}
