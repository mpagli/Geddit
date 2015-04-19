package ch.epfl.fbhack.geddit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ch.epfl.fbhack.geddit.data.ApiRequester;
import ch.epfl.fbhack.geddit.data.ApiResponse;


public class Activity_Main extends ActionBarActivity implements AdapterView.OnItemClickListener {

    // Loaded at the beginning (and when refreshing) => check if null
    private final ArrayList<String> sgIDs = new ArrayList<>();
    private final ArrayList<String> sgNames = new ArrayList<>();
    private final ArrayList<String> sgDistances = new ArrayList<>();
    private CustomAdapterSubgeddit adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.subgeddits_list);
        adapter = new CustomAdapterSubgeddit(this, sgNames, sgDistances);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        buildSubgedditsList();

    }

    private void buildSubgedditsList() {
        sgNames.clear();
        sgDistances.clear();

        HashMap<String, String> subgeddits = ApiResponse.getInstance().getSubgedditNames();

        // Build adapter
        Iterator<String> keySetIterator = subgeddits.keySet().iterator();
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            sgIDs.add(key);
            sgNames.add(subgeddits.get(key));
        }

        for(int i=0; i<sgNames.size(); i++) {
            sgDistances.add(""+(int)(Math.random()*200)+"m");
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        // This launch the request to the API => function processApiResponse is called when completed
        new ApiRequester(this).execute();

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_map) {
            Intent intent = new Intent(Activity_Main.this, Activity_Map.class);
            startActivity(intent );
        } else if(id==R.id.action_add) {
            Intent intent = new Intent(Activity_Main.this, Activity_Add.class);
            startActivity(intent );
        } else if(id==R.id.action_refresh) {
            new ApiRequester(this).execute();
        }

        return super.onOptionsItemSelected(item);
    }

    // Called by ApiRequester when the request has been completed
    public void processApiResponse() {
        // Build the view
        buildSubgedditsList();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(Activity_Main.this, Activity_Threads.class);
        intent.putExtra("subgeddit-id", sgIDs.get(position));
        startActivity(intent);
    }
}

