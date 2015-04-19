package ch.epfl.fbhack.geddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Activity_Comments extends ActionBarActivity{//®  implements AdapterView.OnItemClickListener{

    private int subgedditIndex;
    private int threadIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads); // Same simple list for now

        subgedditIndex = getIntent().getIntExtra("subgedditIndex", 0);
        threadIndex = getIntent().getIntExtra("threadIndex", 0);
        buildCommentsList();
    }

    private void buildCommentsList() {

        ArrayList<String> commmentsTitles = Activity_Main.data.getCommentsTitlesFor(subgedditIndex, threadIndex);

//        Toast.makeText(this, threadsTitles.get(0), Toast.LENGTH_SHORT).show();


        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commmentsTitles);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.threads_list);
        list.setAdapter(adapter);

//        list.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subgeddit, menu);
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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        Intent intent = new Intent(Activity_Comments.this, Activity_Comments.class);
//        intent.putExtra("subgedditIndex", position);
//        startActivity(intent);
//    }
}
