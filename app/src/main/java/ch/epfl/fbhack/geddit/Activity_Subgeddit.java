package ch.epfl.fbhack.geddit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Activity_Subgeddit extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgeddit);

        int position = getIntent().getIntExtra("position", 0);
        buildThreadsList(position);
    }

    private void buildThreadsList(int position) {

        ArrayList<String> threadsTitles = Activity_List.data.getThreadsTitlesFor(position);

        Toast.makeText(this, threadsTitles.get(0), Toast.LENGTH_SHORT).show();


        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.thread_item, threadsTitles);

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
}
