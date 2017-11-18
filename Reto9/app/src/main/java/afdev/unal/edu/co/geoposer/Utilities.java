package afdev.unal.edu.co.geoposer;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class Utilities {

    //https://maps.googleapis.com/maps/api/place/radarsearch/json?location=4.6381938,-74.0840464&radius=5000&type=bus_station&key=AIzaSyCSF_vs_pE2kicc1YSNBt3XYmyUjjK04EY
    //https://maps.googleapis.com/maps/api/place/radarsearch/json?location=4.6509641,-74.1699841&radius=1000.0&bus_station=bus_station&key=AIzaSyCSF_vs_pE2kicc1YSNBt3XYmyUjjK04EY

    private static final String TAG = Utilities.class.getSimpleName();
    private static final String SCHEME = "https";
    private static final String BASE_URL_AUTHORITY = "maps.googleapis.com";
    private static final String MAPS_PATH = "maps";
    private static final String API_PATH = "api";
    private static final String PLACE_PATH = "place";
    private static final String RADAR_PATH = "radarsearch";
    private static final String JSON_FORMAT_PATH = "json";
    private static final String LOCATION_PARAM = "location";
    private static final String RADIUS_PARAM = "radius";
    private static final String TYPE_PARAM = "types";
    private static final String KEY_PARAM = "key";

    private static final String places_api_key = "AIzaSyCSF_vs_pE2kicc1YSNBt3XYmyUjjK04EY";
    private static final String parameters = "[art_gallery,hospital,library,museum,restaurant]";

    public static URL buildAPIUrl(LatLng requested_location, double radius){
        Uri.Builder builder = new Uri.Builder();
        String location_string = requested_location.latitude + "," + requested_location.longitude;
        Log.d(TAG, "location string = " + location_string);

        builder.scheme(SCHEME)
                .authority(BASE_URL_AUTHORITY)
                .appendEncodedPath(MAPS_PATH)
                .appendEncodedPath(API_PATH)
                .appendEncodedPath(PLACE_PATH)
                .appendEncodedPath(RADAR_PATH)
                .appendEncodedPath(JSON_FORMAT_PATH)
                .appendQueryParameter(LOCATION_PARAM, location_string)
                .appendQueryParameter(RADIUS_PARAM, String.valueOf(radius))
                .appendQueryParameter(TYPE_PARAM, parameters)
                .appendQueryParameter(KEY_PARAM, places_api_key)
                .build();

        URL url = null;
        try{
            url = new URL(builder.build().toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Built URL " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
