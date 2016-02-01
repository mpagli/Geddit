package ch.epfl.fbhack.geddit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ch.epfl.fbhack.geddit.data.ApiRequester;
import ch.epfl.fbhack.geddit.data.ApiRequesterVote;
import ch.epfl.fbhack.geddit.data.ApiResponse;


public class Activity_Threads extends ActionBarActivity  implements AdapterView.OnItemClickListener{

    private String subgedditID;
    private final ArrayList<String> threadsTitles = new ArrayList<>();
    private final ArrayList<String> threadsScores = new ArrayList<>();
    private CustomAdapterThread adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        ListView list = (ListView) findViewById(R.id.threads_list);
        adapter = new CustomAdapterThread(this, threadsTitles, threadsScores);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        subgedditID = getIntent().getStringExtra("subgeddit-id");
        buildThreadsList();
    }

    @Override
    protected void onResume() {
        // This launch the request to the API => function processApiResponse is called when completed
        new ApiRequester(this).execute();

        super.onResume();
    }

    private void buildThreadsList() {
        threadsTitles.clear();
        threadsTitles.addAll(ApiResponse.getInstance().getThreadsTitlesFor(subgedditID));
        threadsScores.clear();
        threadsScores.addAll(ApiResponse.getInstance().getThreadsScoresFor(subgedditID));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_threads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.action_add) {
            Intent intent = new Intent(Activity_Threads.this, Activity_Add_Thread.class);
            intent.putExtra(Activity_Add_Thread.SUBGEDDIT_ID_EXTRA, subgedditID);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Activity_Threads.this, Activity_Comments.class);
        intent.putExtra("subgedditID", subgedditID);
        intent.putExtra("threadIndex", position);
        startActivity(intent);
    }

    public void onUpvote(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> threadsIDs = ApiResponse.getInstance().getThreadsIDsFor(subgedditID);
        new ApiRequesterVote(subgedditID, threadsIDs.get(position), "", true).execute();

        new ApiRequester(this).execute();
    }

    public void onDownvote(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> threadsIDs = ApiResponse.getInstance().getThreadsIDsFor(subgedditID);
        new ApiRequesterVote(subgedditID, threadsIDs.get(position), "", false).execute();

        new ApiRequester(this).execute();
    }

    public void processApiResponse() {
        buildThreadsList();
    }
}
