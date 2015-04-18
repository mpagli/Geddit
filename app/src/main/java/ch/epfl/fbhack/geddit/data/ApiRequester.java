package ch.epfl.fbhack.geddit.data;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import ch.epfl.fbhack.geddit.Geddit_main;

/**
 * Created by fred on 18/04/15.
 *
 * This class performs the request asynchronously, and call MainActivity.processApiResponse(...)
 * when data has been loaded (and parsed).
 */
public class ApiRequester extends AsyncTask<Void, Integer, String> {

    private Geddit_main mainActivity;

    private static final int TIMEOUT = 15000;
    private static final String BASE_URL = "http://713f665696.testurl.ws/api/?action=read";

    public ApiRequester(Geddit_main mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return getJsonString(BASE_URL, TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getJsonString(String url, int timeout) throws IOException {
        HttpURLConnection c = (HttpURLConnection) (new URL(url)).openConnection();
        c.setRequestMethod("GET");
        c.setUseCaches(false);
        c.setConnectTimeout(timeout);
        c.setReadTimeout(timeout);
        c.connect();
        int status = c.getResponseCode();

        StringBuilder sb = new StringBuilder();
        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
        }

        c.disconnect();
        return sb.toString();
    }


    @Override
    protected void onPostExecute(String jsonString) {

        if(jsonString == null){
            Toast.makeText(mainActivity.getApplicationContext(), "Something wrong happened",
                    Toast.LENGTH_SHORT).show();
        }

        ArrayList<Subgeddit> subgedditsList = parseData(jsonString);

        mainActivity.processApiResponse(subgedditsList);
    }

    private ArrayList<Subgeddit> parseData(String jsonString) {

        ArrayList<Subgeddit> subgedditsArray = null;

        try {
            JSONObject jData = new JSONObject(jsonString);
            JSONObject jSubgeddits = jData.getJSONObject("subgeddit");
            Iterator<String> keysIt = jSubgeddits.keys();

            subgedditsArray = new ArrayList<>(jSubgeddits.length());
            while(keysIt.hasNext()){
                String latLng = keysIt.next();
                JSONObject jSubgeddit = jSubgeddits.getJSONObject(latLng);

                String name = jSubgeddit.getString("name");

                // Create the Subgeddit object
                Subgeddit subgeddit = new Subgeddit(latLng, name);

                subgedditsArray.add(subgeddit);
            }

            return subgedditsArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
