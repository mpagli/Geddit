package ch.epfl.fbhack.geddit.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by fred on 19/04/15.
 */
public class ApiResponse {
    private static final String NAME = "name";

    private final JSONObject response;

    public ApiResponse(JSONObject response){
        this.response = response;
    }

    public ArrayList<String> getSgNames() {
        ArrayList<String> list = new ArrayList<>(response.length());

        Iterator<String> keysIt = response.keys();
        while (keysIt.hasNext()) {
            JSONObject subgeddit = response.optJSONObject(keysIt.next());
            list.add(subgeddit.optString(NAME));

        }

        return list;
    }

//    Iterator<String> keysIt = jSubgeddits.keys();
//
//    subgedditsArray = new ArrayList<>(jSubgeddits.length());
//    while(keysIt.hasNext()){
//        String latLng = keysIt.next();
//        JSONObject jSubgeddit = jSubgeddits.getJSONObject(latLng);
//
//        String name = jSubgeddit.getString("name");
//
//        // Create the Subgeddit object
//        Subgeddit subgeddit = new Subgeddit(latLng, name);
//
//
//
//        subgedditsArray.add(subgeddit);
//    }
}
