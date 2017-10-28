package co.edu.unal.tictactoe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseData {
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
}
