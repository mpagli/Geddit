package ch.epfl.fbhack.geddit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.epfl.fbhack.geddit.data.ApiResponse;


public class Activity_Comments extends ActionBarActivity{

    private String subgedditID;
    private int threadIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments); // Same simple list for now

        subgedditID = getIntent().getStringExtra("subgedditID");
        threadIndex = getIntent().getIntExtra("threadIndex", 0);
        buildCommentsList();
    }

    private void buildCommentsList() {

        ArrayList<String> commmentsTitles = ApiResponse.getInstance().getCommentsTitlesFor(subgedditID, threadIndex);
        ArrayList<String> commmentsBodies = ApiResponse.getInstance().getCommentsBodyFor(subgedditID, threadIndex);
        ArrayList<String> commmentsScores = ApiResponse.getInstance().getCommentsScoreFor(subgedditID, threadIndex);

        String firstComment_title = commmentsTitles.get(0);
        String firstComment_body = commmentsBodies.get(0);

        commmentsTitles.remove(0);
        commmentsBodies.remove(0);
        commmentsScores.remove(0);

//        Toast.makeText(this, threadsTitles.get(0), Toast.LENGTH_SHORT).show();

        TextView tv1 = (TextView) findViewById(R.id.firstComment_title);
        tv1.setText(firstComment_title);

        TextView tv2 = (TextView) findViewById(R.id.firstComment_body);
        tv2.setText(firstComment_body);

        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commmentsBodies);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.comments_list);
        list.setAdapter(adapter);
        list.setAdapter(new CustomAdapterComment(this, commmentsBodies, commmentsScores));

//        list.setOnItemClickListener(this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
