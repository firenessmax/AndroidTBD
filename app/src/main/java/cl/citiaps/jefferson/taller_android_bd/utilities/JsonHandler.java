package cl.citiaps.jefferson.taller_android_bd.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: Jefferson Morales De la Parra
 * Clase que se utiliza para manipular objetos JSON
 */
public class JsonHandler {

    /**
     * Método que recibe un JSONArray en forma de String y devuelve un String[] con los actores
     */
    public String[] getActors(String actors) {
        try {
            JSONArray ja = new JSONArray(actors);
            String[] result = new String[ja.length()];
            String actor;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject row = ja.getJSONObject(i);
                actor = " " + row.getString("firstName") + " " + row.getString("lastName");
                result[i] = actor;
            }
            return result;
        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }// getActors(String actors)

}// JsonHandler