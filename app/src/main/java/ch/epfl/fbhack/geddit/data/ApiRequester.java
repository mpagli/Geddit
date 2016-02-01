package ch.epfl.fbhack.geddit.data;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.epfl.fbhack.geddit.Activity_Main;
import ch.epfl.fbhack.geddit.Activity_Map;
import ch.epfl.fbhack.geddit.Activity_Threads;

/**
 * Created by fred on 18/04/15.
 *
 * This class performs the request asynchronously, and call MainActivity.processApiResponse(...)
 * when data has been loaded (and parsed).
 */
public class ApiRequester extends AsyncTask<Void, Integer, String> {

    private Activity_Main mainActivity;
    private Activity_Map mapActivity;
    private Activity_Threads threadsActivity;

    private Activity act;

    private static final int TIMEOUT = 15000;
    private static final String BASE_URL = "http://713f665696.testurl.ws/api/?action=read";

    public ApiRequester(Activity_Threads threadsActivity){
        act = threadsActivity;
        this.threadsActivity = threadsActivity;

    }
    public ApiRequester(Activity_Main callingActivity){
        act = callingActivity;
        this.mainActivity = callingActivity;
    }
    public ApiRequester(Activity_Map callingActivity){
        act = callingActivity;
        this.mapActivity = callingActivity;
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
            Toast.makeText(act.getApplicationContext(), "Something wrong happened in the network",
                    Toast.LENGTH_SHORT).show();
        }

        parseData(jsonString);
        if(mainActivity != null)
            mainActivity.processApiResponse();
        else if(mapActivity != null)
            mapActivity.processApiResponse();
        else if(threadsActivity != null)
            threadsActivity.processApiResponse();

        mainActivity = null;
        mapActivity = null;
        threadsActivity = null;
        act = null;
    }

    private void parseData(String jsonString) {
        if(jsonString == null) return;
        try {
            JSONObject jData = new JSONObject(jsonString);
            JSONObject subgeddits = jData.getJSONObject("subgeddit");
            ApiResponse.getInstance().setData(subgeddits);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
