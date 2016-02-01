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

import org.osmdroid.util.GeoPoint;

import ch.epfl.fbhack.geddit.data.ApiAdder;

/**
 * Created by fred on 19/04/15.
 *
 * Activity called from Activity_Main, in order to add a new subgeddit
 */
public class Activity_Add extends Activity implements View.OnClickListener {

    private static final String NO_LOCATION_FOUND = "No location found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        // Ask for location
        setLocationManager();

        // Set button callback
        ((Button)findViewById(R.id.button)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // When the button is clicked, send data to the server (doesn't refresh!)

        String subgedditId = ((TextView) findViewById(R.id.location)).getText().toString();
        String commentTitle = ((TextView) findViewById(R.id.comment_title)).getText().toString();
        String commentBody = ((TextView) findViewById(R.id.comment_body)).getText().toString();

        if(subgedditId.equals(NO_LOCATION_FOUND)){
            Toast.makeText(getApplicationContext(), "Cannot proceed without location",
                    Toast.LENGTH_SHORT).show();
        } else {
            (new ApiAdder(subgedditId, null, commentTitle, commentBody)).execute();
        }

//        // TODO uncomment for debugging
//        TextView requestView = (TextView) findViewById(R.id.request);
        String a = (new ApiAdder(subgedditId, null, commentTitle, commentBody)).formUrl();
        Log.e("URL", a);
//        requestView.setText(a);

        finish();
    }

    private void setLocationManager() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            public void onLocationChanged(Location location) {
                TextView myLocationText = (TextView) findViewById(R.id.location);
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                myLocationText.setText(lat+","+lng);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                TextView myLocationText = (TextView) findViewById(R.id.location);
                myLocationText.setText(NO_LOCATION_FOUND);
            }
        });
    }

}
