package ch.epfl.fbhack.geddit;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ch.epfl.fbhack.geddit.data.ApiAdder;

/**
 * Created by fred on 19/04/15.
 *
 * Activity called from Activity_Main, in order to add a new subgeddit
 */
public class Activity_Add_Thread extends Activity implements View.OnClickListener {
    public static final String SUBGEDDIT_ID_EXTRA = "subgeddit-id-extra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_thread);

        String subgedditId = getIntent().getStringExtra(SUBGEDDIT_ID_EXTRA);
        ((TextView) findViewById(R.id.location)).setText(subgedditId);

        // Set button callback
        ((Button)findViewById(R.id.button)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // When the button is clicked, send data to the server (doesn't refresh!)

        String subgedditId = ((TextView) findViewById(R.id.location)).getText().toString();
        String commentTitle = ((TextView) findViewById(R.id.comment_title)).getText().toString();
        String commentBody = ((TextView) findViewById(R.id.comment_body)).getText().toString();

        (new ApiAdder(subgedditId, null, commentTitle, commentBody)).execute();

//        // TODO to comment
//        TextView requestView = (TextView) findViewById(R.id.request);
//        String a = (new ApiAdder(subgedditId, null, commentTitle, commentBody)).formUrl();
//        requestView.setText(a);
        String a = (new ApiAdder(subgedditId, null, commentTitle, commentBody)).formUrl();
        Log.e("URL", a);


        finish();
    }
}
