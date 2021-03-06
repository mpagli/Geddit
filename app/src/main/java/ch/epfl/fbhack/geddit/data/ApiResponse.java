package ch.epfl.fbhack.geddit.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by fred on 19/04/15.
 */
public class ApiResponse {

    private static ApiResponse instance;

    private static final Map<String, Subgeddit> subgeddits = new HashMap<>();

    public synchronized static ApiResponse getInstance() {
        if (instance==null)
        {
            instance = new ApiResponse();
            Log.w("ApiResponse", "ApiResponse Class Object created!");
        }
        else{
            Log.w("ApiResponse", "ApiResponse Class Object not created just returned existing one!");
        }
        return instance;
    }

    public void setData(JSONObject response){
        Iterator<String> sgIt = response.keys();
        subgeddits.clear();
        while (sgIt.hasNext()) {
            String latLng = sgIt.next();
            JSONObject jSubgeddit = response.optJSONObject(latLng);
            Subgeddit subgeddit = new Subgeddit(latLng, jSubgeddit);
            subgeddits.put(latLng, subgeddit);
        }
    }

    // ##################  Getters  ##################
    public HashMap<String, String> getSubgedditNames() {
        HashMap<String, String> sgIdNames = new HashMap<>();

        // Create simple HashMap of latLon <-> Title
        Iterator<String> keySetIterator = subgeddits.keySet().iterator();
        while(keySetIterator.hasNext()){
            String latLon = keySetIterator.next();
            sgIdNames.put(latLon, subgeddits.get(latLon).name);
        }

        return sgIdNames;
    }

    public ArrayList<String> getThreadsIDsFor(String subgedditID) {
        ArrayList<Thread> threads = subgeddits.get(subgedditID).threads;

        ArrayList<String> threadsIDs = new ArrayList<>(threads.size());
        for(Thread thread: threads){
            threadsIDs.add(thread.id);
        }
        return threadsIDs;
    }

    public ArrayList<String> getThreadsTitlesFor(String subgedditID) {
        ArrayList<Thread> threads = subgeddits.get(subgedditID).threads;

        ArrayList<String> threadsTitles = new ArrayList<>(threads.size());
        for(Thread thread:threads){
            threadsTitles.add(thread.title);
        }
        return threadsTitles;
    }

    public ArrayList<String> getThreadsScoresFor(String subgedditIndex) {
        ArrayList<Thread> threads = subgeddits.get(subgedditIndex).threads;

        ArrayList<String> threadsScores = new ArrayList<>(threads.size());
        for(Thread thread:threads){
            threadsScores.add(String.valueOf(thread.upvote - thread.downvote));
        }
        return threadsScores;
    }

    public ArrayList<String> getCommentsTitlesFor(String subgedditIndex, int threadIndex) {
        ArrayList<Comment> comments = subgeddits.get(subgedditIndex).threads.get(threadIndex).comments;

        ArrayList<String> commentsTitles = new ArrayList<>(comments.size());
        for(Comment comment:comments){
            commentsTitles.add(comment.title);
        }
        return commentsTitles;
    }

    public ArrayList<String> getCommentsBodyFor(String subgedditIndex, int threadIndex) {
        ArrayList<Comment> comments = subgeddits.get(subgedditIndex).threads.get(threadIndex).comments;

        ArrayList<String> commentsBodies = new ArrayList<>(comments.size());
        for(Comment comment:comments){
            commentsBodies.add(comment.body);
        }
        return commentsBodies;
    }

    public ArrayList<String> getCommentsScoreFor(String subgedditIndex, int threadIndex) {
        ArrayList<Comment> comments = subgeddits.get(subgedditIndex).threads.get(threadIndex).comments;
        ArrayList<String> scores = new ArrayList<String>();
        for(Comment comment:comments){
            scores.add(((Integer)(comment.upvote-comment.downvote)).toString());
        }
        return scores;
    }

    // ##################  Private container classes  ##################
    private class Subgeddit {
        public final String name;
        public final ArrayList<Thread> threads;

        public Subgeddit(String latLng, JSONObject jSubgeddit){
            name = jSubgeddit.optString("name");

            JSONObject jThreads = jSubgeddit.optJSONObject("thread");
            threads = new ArrayList<>(jThreads.length());
            Iterator<String> it = jThreads.keys();
            while(it.hasNext()){
                String threadId = it.next();
                JSONObject jThread = jThreads.optJSONObject(threadId);

                Thread thread = new Thread(threadId, jThread);
                threads.add(thread);
            }
        }
    }

    private class Thread {
        public final String id;
        public final String title;
        public final int upvote;
        public final int downvote;
        public final ArrayList<Comment> comments;

        public Thread(String threadId, JSONObject jThread) {
            id = threadId;
            title = jThread.optString("title");
            upvote = jThread.optInt("upvote");
            downvote = jThread.optInt("downvote");

            JSONArray jComments = jThread.optJSONArray("comment");
            comments = new ArrayList<>(jComments.length());
            for(int i=0; i<jComments.length(); i++){
                Comment comment = new Comment(jComments.optJSONObject(i));
                comments.add(comment);
            }
        }
    }

    private class Comment {
        public final String title;
        public final String body;
        public final int upvote;
        public final int downvote;
        public final long dateTime;

        public Comment(JSONObject jComment) {
            title = jComment.optString("title");
            body = jComment.optString("body");
            upvote = jComment.optInt("upvote");
            downvote = jComment.optInt("downvote");
            dateTime = jComment.optLong("dateTime");
        }
    }


}
