package ch.epfl.fbhack.geddit;

/**
 * Created by surrel on 19.04.15.
 */
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
    Context context;

    public MyItemizedOverlay(Drawable marker, Context c) {
        super(marker, new ResourceProxyImpl(c));
        //super(boundCenterBottom(marker));
        // TODO Auto-generated constructor stub
        populate();
        context = c;
    }

    @Override
    protected boolean onTap(int index) {

        Intent intent = new Intent(context, subgeddit.class);
        intent.putExtra("geddit-id", overlayItemList.get(index).getTitle());
        context.startActivity(intent);

        return true;
    }

    public void addItem(GeoPoint p, String title, String snippet){
        OverlayItem newItem = new OverlayItem(snippet, title, p);
        overlayItemList.add(newItem);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        // TODO Auto-generated method stub
        return overlayItemList.get(i);
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return overlayItemList.size();
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        // TODO Auto-generated method stub
        super.draw(canvas, mapView, shadow);
    }

    @Override
    public boolean onSnapToItem(int i, int i1, Point point, IMapView iMapView) {
        return false;
    }
}