package com.example.healthtimer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import activity.BasicActivity;


public class Add_events extends AppCompatActivity {

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/events");
    private FirebaseAuth mAuth;
    FirebaseUser user;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_events);

        Button btn_add_event = (Button) findViewById(R.id.btn_add_event);

        btn_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String events = ((EditText) findViewById(R.id.edit_add_event)).getText().toString();


                if (events.length() > 0 ) {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    user = mAuth.getCurrentUser();

                    Events_info events_info = new Events_info(events, user.getUid());

                    db.collection("test").add(events_info)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    StartToast("등록 완료.");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    StartToast("등록 실패.");
                                }
                            });

                }else{
                    StartToast("운동종목을 입력하여 주세요.");

                }

            }
        });

    }




    private void StartToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void Movetolist(){
        Intent intent = new Intent(this, Timer_Menu.class);
        startActivity(intent);
    }

*/
}
