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

import ch.epfl.fbhack.geddit.Activity_Main;
import ch.epfl.fbhack.geddit.Activity_Map;

/**
 * Created by fred on 18/04/15.
 *
 * This class performs the request asynchronously, and call MainActivity.processApiResponse(...)
 * when data has been loaded (and parsed).
 */
public class ApiAdder extends AsyncTask<Void, Integer, Void> {

    private static final int TIMEOUT = 15000;
    private static final String BASE_URL = "http://713f665696.testurl.ws/api/?action=comment";

    private String subgedditId;
    private String threadId; // Take comment title if not set
    private String commentTitle;
    private String commentBody;

    public ApiAdder(String subgedditId, String threadId, String commentTitle, String commentBody) {
        this.subgedditId = subgedditId;
        this.threadId = threadId;
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String url = formUrl();
            getJsonString(url, TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String formUrl() {
        String url = BASE_URL + "&subgeddit-id="+subgedditId;
        if(threadId != null) {
            url += "&thread-id=" + threadId;
        }
        url += "&comment-title="+commentTitle;
        url += "&comment-body="+commentBody;

        return url;
    }

    private void getJsonString(String url, int timeout) throws IOException {
        HttpURLConnection c = (HttpURLConnection) (new URL(url)).openConnection();
        c.setRequestMethod("GET");
        c.setUseCaches(false);
        c.setConnectTimeout(timeout);
        c.setReadTimeout(timeout);
        c.connect();
        int status = c.getResponseCode();
        c.disconnect();
    }
}
