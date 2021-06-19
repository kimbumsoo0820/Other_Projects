package com.example.healthtimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class Add_events_test extends AppCompatActivity {

    private void StartToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_events);

        Button btn_add_event = (Button)findViewById(R.id.btn_add_event);
        EditText edit_add_event = (EditText)findViewById(R.id.edit_add_event);
        TextView text = (TextView)findViewById(R.id.text);
        final String[] events = new String[1];





        btn_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                events[0] = edit_add_event.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference rootRef = firebaseDatabase.getReference();
                DatabaseReference dataRef = rootRef.child("events"); //
                dataRef.push().setValue(events[0]);
                DatabaseReference memberRef = rootRef.child("Add_events");
                DatabaseReference itemRef = memberRef.push();
                itemRef.child("events").setValue(events[0]);

                memberRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        StringBuffer buffer = new StringBuffer();
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                buffer.append(ds.getKey() + " : " + ds.getValue() + "\n");
                            }
                            buffer.append("\n");
                        }
                        //text.setText(events[0]);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(Add_events_test.this, Add_event_main.class);
                startActivity(intent);



            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

}
