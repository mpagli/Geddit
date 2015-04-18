package ch.epfl.fbhack.geddit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ch.epfl.fbhack.geddit.data.ApiRequester;
import ch.epfl.fbhack.geddit.data.Subgeddit;


public class Geddit_main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geddit_main);

        // This launch the request to the API => function processApiResponse is called when completed
        new ApiRequester(this).execute();

        populateListViewSubgeddit();
    }

    private void populateListViewSubgeddit() {
        //create list of items
        String[] myItems = {"EPFL hackathon, 1m", "Indian restaurant, 20m", "IC Library, 2m"};

        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.subgeddit_list_item, myItems);

        //configure the list view
        ListView list = (ListView) findViewById(R.id.listView_main_subgeddit);
        list.setAdapter(adapter);
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
            Intent intent = new Intent(Geddit_main.this, activity_map.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    // Called by ApiRequester when the request has been completed
    public void processApiResponse(ArrayList<Subgeddit> subgedditsList) {

        Toast.makeText(getApplicationContext(), subgedditsList.get(0).getLatLng(),
                Toast.LENGTH_SHORT).show();

        Toast.makeText(getApplicationContext(), subgedditsList.get(0).getName(),
                Toast.LENGTH_SHORT).show();
    }
}

