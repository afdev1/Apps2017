package afdev.unal.edu.co.webservices;

import android.util.Log;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Consts {
    public static String readResponse(HttpResponse res) {
        InputStream is;
        String return_text = "";
        try {
            is = res.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
        } catch (Exception e) {
            Log.e("Error" , e.getMessage());
        }
        return return_text;
    }
}
