package bs.bj.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Named;
import java.util.Objects;

/**
 * Created by boubdyk on 04.11.2015.
 */

@Named
public class Helper {

    public Helper() {}


    /**
     * @param input json file which need to parse
     * @return JSONObject
     */
    public JSONObject parse(final String input) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = (Object)parser.parse(input);
            return (JSONObject) obj;
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }
}
