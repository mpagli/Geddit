package ch.epfl.fbhack.geddit.data;

/**
 * Created by fred on 18/04/15.
 */
public class Subgeddit {
    private final String latLng;
    private final String name;


    public Subgeddit(String latLng, String name) {
        this.latLng = latLng;
        this.name = name;
    }

    public String getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
