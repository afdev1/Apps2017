package co.edu.unal.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseData.test();


        Button offline = (Button) findViewById(R.id.bt_offline);
        Button online = (Button) findViewById(R.id.bt_online);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final Button create = (Button) findViewById(R.id.bt_create);
//        final Button join = (Button) findViewById(R.id.bt_join);
        final TextView matches = (TextView) findViewById(R.id.tv_matches);
//        final LinearLayout fake = (LinearLayout) findViewById(R.id.fake);
        final ListView listView = (ListView) findViewById(R.id.lv_matches);

        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
                intent.putExtra("username", "");
                finish();
                startActivity(intent);
            }
        });

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matches.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                username.setVisibility(View.VISIBLE);
                create.setVisibility(View.VISIBLE);
//                fake.setVisibility(View.VISIBLE);
            }
        });

//        join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
//                intent.putExtra("username", username.getText().toString());
//                finish();
//                startActivity(intent);
//            }
//        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseData.newMatch(username.getText().toString());
                Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
                intent.putExtra("owner", username.getText().toString());
                intent.putExtra("owning", true);
                finish();
                startActivity(intent);
            }
        });


        ///////////////////////
        DatabaseReference mDatabase = FirebaseData.mDatabase.child("matches");
        arr = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    map2list((Map) dataSnapshot.getValue());
                    //formats the datasnapshot entries to strings
                    adapter.notifyDataSetChanged();
                } catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(listener);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arr);

//        ListView listView = (ListView) findViewById(R.id.lv_matches);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] ar = ((TextView) view).getText().toString().split(" ");
                if(ar.length > 1){
                    Toast toast = Toast.makeText(MainActivity.this, "Already matched! Please choose another or create a new one.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(!username.getText().toString().isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
                    intent.putExtra("owner", ar[0]);
                    intent.putExtra("username", username.getText().toString());
                    finish();
                    FirebaseData.setEnemy(ar[0], username.getText().toString());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Please, write your username.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //////////////////////
    }

    public ArrayList<String> arr;
    public void map2list(Map<String,HashMap<String, HashMap<String, Object>>> map){

        arr.clear();
        for (Map.Entry<String, HashMap<String, HashMap<String, Object>>> entry : map.entrySet()) {

            String key = entry.getKey();
            HashMap<String, HashMap<String, Object>> value = entry.getValue();
            String enemy = value.get("state").get("enemy").toString();
            arr.add(key + (!enemy.equals("\u0000")?" vs. " + enemy:""));// + ": " + value);
        }

    }
}
