package co.edu.unal.tictactoe;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.harding.tictactoe.TicTacToeGame;

public class FirebaseData {
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void test() {
        Log.v("FIREBASE", mDatabase.child("matches").child("user1").toString());
    }

    public static void newMatch(String host) {
        DatabaseReference match = mDatabase.child("matches").child(host).child("state");
        //Enemy points
        match.child("ep").setValue(0);
        //Owner points
        match.child("op").setValue(0);
        //Tie points
        match.child("tp").setValue(0);
        match.child("enemy").setValue("\u0000");
        String open = String.valueOf(TicTacToeGame.OPEN_SPOT);
        String[] initial = new String[]{open, open, open, open, open, open, open, open, open};
        List nameList = new ArrayList<>(Arrays.asList(initial));
        match.child("state").setValue(nameList);
        //0 for owner - 1 for enemy
        match.child("turn").setValue(0);
    }

    public static void update(String host, char[] mBoard){
        DatabaseReference match = mDatabase.child("matches").child(host).child("state").child("state");
        String[] initial = new String[9];
        for(int i = 0; i < 9; i++){
            initial[i] = String.valueOf(mBoard[i]);
        }
        List nameList = new ArrayList<>(Arrays.asList(initial));
        match.setValue(nameList);
    }

    public static void setEnemy(String host, String enemy){
        mDatabase.child("matches").child(host).child("state").child("enemy").setValue(enemy);
    }
}
