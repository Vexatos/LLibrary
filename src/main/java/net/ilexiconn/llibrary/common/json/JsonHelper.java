package net.ilexiconn.llibrary.common.json;

import net.ilexiconn.llibrary.common.json.container.JsonTabulaModel;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class for parsing json files to containers.
 *
 * @author iLexiconn
 * @since 0.1.0
 */
public class JsonHelper {
    public static JsonTabulaModel parseTabulaModel(InputStream stream) {
        return JsonFactory.getGson().fromJson(new InputStreamReader(stream), JsonTabulaModel.class);
    }
}
