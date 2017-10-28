package co.edu.unal.tictactoe;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseData {
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void test() {
        Log.v("FIREBASE", mDatabase.child("matches").child("user1").toString());
    }
}
