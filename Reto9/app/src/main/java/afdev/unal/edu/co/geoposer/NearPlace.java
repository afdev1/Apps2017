package afdev.unal.edu.co.geoposer;

import com.google.android.gms.maps.model.LatLng;

public class NearPlace {
    private LatLng location;
    private double radius;

    public NearPlace(LatLng _location, double _radius){
        location = _location;
        radius = _radius;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    public LatLng getLocation() {
        return location;
    }

    public double getRadius() {
        return radius;
    }
}
