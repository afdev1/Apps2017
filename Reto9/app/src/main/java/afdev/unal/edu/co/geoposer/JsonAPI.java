package afdev.unal.edu.co.geoposer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonAPI {

    public static String[] parseNearPlacesJSON(Context context, String placesListJsonStr) throws JSONException {
        final String GPA_STATUS = "status";
        final String GPA_RESULTS = "results";

        final String GPA_STATUS_OK = "OK";
        final String GPA_STATUS_ZERO_RESULTS = "REQUEST_DENIED";
        final String GPA_ID = "place_id";

        String[] stationLocations;

        JSONObject placesJson = new JSONObject(placesListJsonStr);

        if(placesJson.has(GPA_STATUS)){
            String status = placesJson.getString(GPA_STATUS);
            if(status.equals(GPA_STATUS_ZERO_RESULTS)){
                return new String[0];
            }
            else if(!status.equals(GPA_STATUS_OK) ) {
                return null;
            }
        }

        JSONArray locationsArray = placesJson.getJSONArray(GPA_RESULTS);
        stationLocations = new String[locationsArray.length()];

        for(int i = 0; i <locationsArray.length(); i++){
            String location_id;
            JSONObject locationInfo = locationsArray.getJSONObject(i);
            location_id = locationInfo.getString(GPA_ID);
            stationLocations[i] = location_id;
        }
        return stationLocations;
    }
}