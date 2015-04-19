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
import android.widget.Toast;

import java.util.ArrayList;

import ch.epfl.fbhack.geddit.data.ApiRequester;
import ch.epfl.fbhack.geddit.data.ApiResponse;


public class Activity_List extends ActionBarActivity implements AdapterView.OnItemClickListener {

    // Loaded at the beginning (and when refreshing) => check if null
    public static ApiResponse data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geddit_main);

        // This launch the request to the API => function processApiResponse is called when completed
        new ApiRequester(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geddit_main, menu);
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
        } else if(id==R.id.action_map){
            Intent intent = new Intent(Activity_List.this, Activity_Map.class);
            startActivity(intent );
        }

        return super.onOptionsItemSelected(item);
    }

    // Called by ApiRequester when the request has been completed
    public void processApiResponse(ApiResponse data) {
        // Save data in global variable (BAD!!!)
        Activity_List.data = data;

        // Build the view
        buildSgList();
    }

    private void buildSgList() {
        ArrayList<String> sgNames = data.getSgNames();

        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.subgeddit_list_item, sgNames);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.listView_main_subgeddit);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Not working yet (=> then launch new activity)
//        Toast.makeText(this.getApplicationContext(), position,
//                Toast.LENGTH_SHORT).show();

    }
}

