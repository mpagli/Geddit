package ch.epfl.fbhack.geddit.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by fred on 19/04/15.
 */
public class ApiResponse {

    private ArrayList<Subgeddit> subgeddits;

//    private ArrayList<JSONObject> threads;

    public ApiResponse(JSONObject response){
        subgeddits = new ArrayList<>(response.length());

        Iterator<String> sgIt = response.keys();
        while (sgIt.hasNext()) {
            String latLng = sgIt.next();
            JSONObject jSubgeddit = response.optJSONObject(latLng);
            Subgeddit subgeddit = new Subgeddit(latLng, jSubgeddit);
            subgeddits.add(subgeddit);
        }
    }

    // ##################  Getters  ##################
    public ArrayList<String> getSubgedditNames() {
        ArrayList<String> sgNames = new ArrayList<>(subgeddits.size());
        for(Subgeddit sg:subgeddits){
            sgNames.add(sg.name);
        }
        return sgNames;
    }

    public ArrayList<String> getThreadsTitlesFor(int subgedditIndex) {
        ArrayList<Thread> threads = subgeddits.get(subgedditIndex).threads;

        ArrayList<String> threadsTitles = new ArrayList<>(threads.size());
        for(Thread thread:threads){
            threadsTitles.add(thread.title);
        }
        return threadsTitles;
    }

    public ArrayList<String> getThreadsScoresFor(int subgedditIndex) {
        ArrayList<Thread> threads = subgeddits.get(subgedditIndex).threads;

        ArrayList<String> threadsScores = new ArrayList<>(threads.size());
        for(Thread thread:threads){
            threadsScores.add(String.valueOf(thread.upvote - thread.downvote));
        }
        return threadsScores;
    }

    public ArrayList<String> getCommentsTitlesFor(int subgedditIndex, int threadIndex) {
        ArrayList<Comment> comments = subgeddits.get(subgedditIndex).threads.get(threadIndex).comments;

        ArrayList<String> commentsTitles = new ArrayList<>(comments.size());
        for(Comment comment:comments){
            commentsTitles.add(comment.title);
        }
        return commentsTitles;
    }


    // ##################  Private container classes  ##################
    private class Subgeddit {
        public String latLng;
        public String name;
        public ArrayList<Thread> threads;

        public Subgeddit(String latLng, JSONObject jSubgeddit){
            this.latLng = latLng;
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
        public String id;
        public String title;
        public int upvote;
        public int downvote;
        public ArrayList<Comment> comments;

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
        public String title;
        public String body;
        public int upvote;
        public int downvote;
        public long dateTime;

        public Comment(JSONObject jComment) {
            title = jComment.optString("title");
            body = jComment.optString("body");
            upvote = jComment.optInt("upvote");
            downvote = jComment.optInt("downvote");
            dateTime = jComment.optLong("dateTime");
        }
    }


}
