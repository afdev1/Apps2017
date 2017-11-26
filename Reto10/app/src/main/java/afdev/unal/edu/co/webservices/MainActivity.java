package afdev.unal.edu.co.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<WifiZone> datos = new ArrayList<>();
    List<WifiZone> datos2 = new ArrayList<>();

    private String[] arraySpinner;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.sp_departamento);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                datos2.clear();
                TextView textView = (TextView) view;
                String q = textView.getText().toString();
                for(WifiZone zone : datos){
                    if(zone.getMunicipio().equals(q)){
                        datos2.add(zone);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mRecyclerView = findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WifiZoneAdapter(datos2, this);
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

                final HashSet<String> municipios = new HashSet<>();
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    try {
                        if (!getDato(jsonObject, "municipio").equals("")) {
                            municipios.add(getDato(jsonObject, "municipio"));
                        }
                    } catch (Exception e) {

                    }

                    datos.add(new WifiZone(getDato(jsonObject, "nombre_zona_wifi"), getDato(jsonObject, "municipio"), getDato(jsonObject, "latitud"),
                            getDato(jsonObject, "longitud"), getDato(jsonObject, "departamento"), getDato(jsonObject, "direccion")));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();

                        arraySpinner = municipios.toArray(new String[municipios.size()]);
                        Arrays.sort(arraySpinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, arraySpinner);
                        spinner.setAdapter(adapter);

                        spinner.setSelection(38);
                    }
                });
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
            }
            return s;
        }
    }

    public String getDato(JSONObject obj, String param) {
        try {
            return obj.getString(param);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
