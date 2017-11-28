package e.utente3academy.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView info;
    private TextView info2;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();

        myRef = database.getReferenceFromUrl("https://hello-6d8d0.firebaseio.com/Users");

        //writeNewUser("diego", "ciao");

        info = findViewById(R.id.t);
        info2 = findViewById(R.id.t2);

        //Stessa cosa di quello sotto
        // Read from the database
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                info.setText("Nome: "+ value.getEmail().toString());
                info2.setText("Password: "+ value.getPassword().toString());
                Log.d("FD_DB", "Value is: " + info.getText().toString() + " "+ info2.getText().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FD_DB", "Failed to read value.", error.toException());
            }
        });*/

        /*ValueEventListener postlistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                info.setText("Nome: " + value.getEmail().toString());
                info2.setText("Password: " + value.getPassword().toString());
                Log.d("FD_DB", "Value is: " + info.getText().toString() + " " + info2.getText().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("FD_DB", "Failed to read value.", databaseError.toException());
            }
        };

        myRef.addValueEventListener(postlistener);*/

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("FD_DB", "OnChildAdded: " + dataSnapshot.getKey());
                User u = dataSnapshot.getValue(User.class);
                info.setText("Nome: " + u.getEmail().toString());
                info2.setText("Password: " + u.getPassword().toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("FD_DB", "OnChildChanged: " + dataSnapshot.getKey());
                User u = dataSnapshot.getValue(User.class);
                String s1 = dataSnapshot.getKey();
                info.setText("Nome: " + u.getEmail().toString());
                info2.setText("Password: " + u.getPassword().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("FD_DB", "OnChildRemoved: " + dataSnapshot.getKey());
                String s1 = dataSnapshot.getKey();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("FD_DB", "OnChildMoved: " + dataSnapshot.getKey());
                User u = dataSnapshot.getValue(User.class);
                String s1 = dataSnapshot.getKey();
                info.setText("Nome: " + u.getEmail().toString());
                info2.setText("Password: " + u.getPassword().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FD_DB", "postsComment:onCancelled" + databaseError.toException());
                Toast.makeText(getApplicationContext(), "Failed to load comments", Toast.LENGTH_SHORT).show();
            }
        };
        myRef.addChildEventListener(childEventListener);
    }

    private void writeNewUser(String email, String pass){
        User u = new User(email, pass);
        myRef.setValue(u);
    }
}
