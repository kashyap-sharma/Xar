package co.jlabs.xar.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by JLabs on 02/21/17.
 */

public class Image implements Serializable {
    private JSONObject jsonArray;

    public Image() {
    }
    public void  setJsonArray(JSONObject jsonArray){
        this.jsonArray=jsonArray;
    }
    public  JSONObject getJsonArray(){
       return jsonArray;
    }
}
