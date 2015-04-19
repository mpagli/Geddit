package ch.epfl.fbhack.geddit.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by fred on 19/04/15.
 */
public class ApiResponse {

    private ArrayList<String> sgNames;
    private ArrayList<JSONObject> threads;

    public ApiResponse(JSONObject response){

        sgNames = new ArrayList<>(response.length());
        threads = new ArrayList<>(response.length());

        Iterator<String> sgIt = response.keys();
        while (sgIt.hasNext()) {
            JSONObject subgeddit = response.optJSONObject(sgIt.next());

            sgNames.add(subgeddit.optString("name"));
            threads.add(subgeddit.optJSONObject("thread"));
        }
    }

    public ArrayList<String> getSgNames() {
        return sgNames;
    }

    public ArrayList<String> getThreadsTitlesFor(int position) {

        JSONObject sgThreads = threads.get(position);

        ArrayList<String> threadsTitles = new ArrayList<>(sgThreads.length());

        Iterator<String> threadsIts = sgThreads.keys();
        while(threadsIts.hasNext()){
            String threadId = threadsIts.next();
            String threadTitle = sgThreads.optJSONObject(threadId).optString("title");

            threadsTitles.add(threadTitle);
        }

        return threadsTitles;
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
