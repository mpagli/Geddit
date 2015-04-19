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
public class ApiRequesterVote extends AsyncTask<Void, Integer, String> {

    private static final int TIMEOUT = 15000;
    private static final String BASE_URL = "http://713f665696.testurl.ws/api/?action=vote";
    private final String subgedditID;
    private final String threadID;
    private final String commentID;
    private final boolean upvote;

    public ApiRequesterVote(String subgedditID, String threadID, String commentID, boolean upvote) {
        super();
        this.subgedditID = subgedditID;
        this.threadID = threadID;
        this.commentID = commentID;
        this.upvote = upvote;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String up = "false";
            if(upvote) up = "on";
            return getJsonString(BASE_URL+"&subgeddit-id="+subgedditID+"&thread-id="+threadID+"&comment-id="+commentID+"&up="+up, TIMEOUT);
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
}
