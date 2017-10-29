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
        final ListView list = (ListView) findViewById(R.id.lv_matches);

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
                list.setVisibility(View.VISIBLE);
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
            }
        });


        ///////////////////////
        DatabaseReference mDatabase = FirebaseData.mDatabase.child("matches");
        arr = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map2list((Map) dataSnapshot.getValue());
                //formats the datasnapshot entries to strings
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(listener);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arr);

        ListView listView = (ListView) findViewById(R.id.lv_matches);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] ar = ((TextView) view).getText().toString().split(" ");
                if(ar.length > 1){
                    return;
                }
                if(!username.getText().toString().isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
                    intent.putExtra("owner", ar[0]);
                    intent.putExtra("username", username.getText().toString());
                    finish();
                    FirebaseData.setEnemy(ar[0], username.getText().toString());
                    startActivity(intent);
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
