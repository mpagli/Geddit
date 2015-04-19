package ch.epfl.fbhack.geddit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ch.epfl.fbhack.geddit.data.ApiRequester;
import ch.epfl.fbhack.geddit.data.ApiResponse;


public class Activity_Map extends ActionBarActivity {

    private MapView mMap;
    private AccuracyCircleOverlay mAccuracyOverlay;
    private MyItemizedOverlay myItemizedOverlay;

    private LocationManager locationManager;
    MapController mapController;
    private ArrayList<OverlayItem> mOverlays;

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.w(this.getClass().getName(), "Position update: " + location);
            updateWithNewLocation(location);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.w(this.getClass().getName(), "Status changed: "+status);
            Log.w(this.getClass().getName(), "        Extras: "+extras.toString());
        }
        public void onProviderEnabled(String provider) {
            Log.w(this.getClass().getName(), "Provider enabled: "+provider);
        }
        public void onProviderDisabled(String provider) {
            Log.w(this.getClass().getName(), "Provider disabled: "+provider);
            updateWithNewLocation(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_map);

        mMap = (MapView) this.findViewById(R.id.mapview);
        mMap.setBuiltInZoomControls(true);
        mMap.setMultiTouchControls(true);
        mapController = (MapController) mMap.getController();
        mapController.setZoom(2);

        mAccuracyOverlay = new AccuracyCircleOverlay(getApplicationContext(), getResources().getColor(R.color.gps_track));
        mMap.getOverlays().add(mAccuracyOverlay);

        // This launch the request to the API => function processApiResponse is called when completed
        new ApiRequester(this).execute();

        Drawable marker = getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        myItemizedOverlay = new MyItemizedOverlay(marker, this);
        mMap.getOverlays().add(myItemizedOverlay);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private void updateWithNewLocation(Location location) {
        String ll;
        TextView myLocationText = (TextView) findViewById(R.id.myLocationText);

        if(location != null && mMap != null && mapController != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            ll = "Lat:"+lat+"  lon:"+lon+"  "+location.toString();

            mapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));

            setUserPositionAt(location);

        } else {
            ll = "No location found";
        }
        myLocationText.setText(ll);
    }

    public void setUserPositionAt(Location location) {
        mAccuracyOverlay.setLocation(location);
        setCenterAndZoom(new GeoPoint(location), 12);
    }

    private void setCenterAndZoom(GeoPoint loc, int zoom) {
        mMap.getController().setZoom(zoom);
        mMap.getController().setCenter(loc);
    }

    public void processApiResponse() {
        HashMap<String, String> subgeddits = ApiResponse.getInstance().getSubgedditNames();

        // Build the marker layer
        myItemizedOverlay.clear();
        Iterator<String> keySetIterator = subgeddits.keySet().iterator();
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            int index = key.indexOf(',');
            Float lat = Float.parseFloat(key.substring(0, index));
            Float lon = Float.parseFloat(key.substring(index+1, key.length()));
            GeoPoint myPoint = new GeoPoint(lat, lon);
            myItemizedOverlay.addItem(myPoint, key, key);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_map, menu);
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
        } else if(id == R.id.action_list) {
            Intent intent = new Intent(Activity_Map.this, Activity_Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if(id==R.id.action_refresh) {
            new ApiRequester(this).execute();
        }

        return super.onOptionsItemSelected(item);
    }
}
