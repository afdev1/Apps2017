package afdev.unal.edu.co.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<WifiZone> datos = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WifiZoneAdapter(datos, this);
        mRecyclerView.setAdapter(mAdapter);

        new ExecuteTask().execute();
    }

    class ExecuteTask extends AsyncTask<String, Integer, String> {

        ExecuteTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            return PostData();
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onCancelled() {
        }

        String PostData() {
            String s = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("https://www.datos.gov.co/resource/f4kx-n3nn.json");//?$limit=100");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                s = Consts.readResponse(httpResponse);

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    datos.add(new WifiZone(getDato(jsonObject, "nombre_zona_wifi"), getDato(jsonObject,"municipio"), getDato(jsonObject,"latitud"),
                            getDato(jsonObject,"longitud"), getDato(jsonObject,"departamento"), getDato(jsonObject,"direccion")));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
            }
            return s;
        }
    }

    public String getDato(JSONObject obj, String param){
        try {
            return obj.getString(param);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
