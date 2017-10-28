package co.edu.unal.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button offline = (Button) findViewById(R.id.bt_offline);
        Button online = (Button) findViewById(R.id.bt_online);

        final EditText username = (EditText) findViewById(R.id.et_username);
        final Button create = (Button) findViewById(R.id.bt_create);
        final Button join = (Button) findViewById(R.id.bt_join);
        final TextView matches = (TextView) findViewById(R.id.tv_matches);
        final LinearLayout fake = (LinearLayout) findViewById(R.id.fake);
        ListView list = (ListView) findViewById(R.id.lv_matches);

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
//                list.setVisibility(View.VISIBLE);
                username.setVisibility(View.VISIBLE);
                create.setVisibility(View.VISIBLE);
                fake.setVisibility(View.VISIBLE);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndroidTicTacToeActivity.class);
                intent.putExtra("username", username.getText().toString());
                finish();
                startActivity(intent);
            }
        });
    }
}
