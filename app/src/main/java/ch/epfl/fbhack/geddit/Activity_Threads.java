package ch.epfl.fbhack.geddit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Activity_Threads extends ActionBarActivity  implements AdapterView.OnItemClickListener{

    private int subgedditIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        subgedditIndex = getIntent().getIntExtra("subgedditIndex", 0);
        buildThreadsList();
    }

    private void buildThreadsList() {

        ArrayList<String> threadsTitles = Activity_Main.data.getThreadsTitlesFor(subgedditIndex);

//        Toast.makeText(this, threadsTitles.get(0), Toast.LENGTH_SHORT).show();

        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.thread_item, threadsTitles);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.threads_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Activity_Threads.this, Activity_Comments.class);
        intent.putExtra("subgedditIndex", subgedditIndex);
        intent.putExtra("threadIndex", position);
        startActivity(intent);
    }
}
