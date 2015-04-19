package ch.epfl.fbhack.geddit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by fred on 19/04/15.
 */
public class Activity_Add extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
    }

    @Override
    public void onClick(View v) {
        // When the button is clicked, send data to the server (doesn't refresh!)

        // TODO
    }
}
