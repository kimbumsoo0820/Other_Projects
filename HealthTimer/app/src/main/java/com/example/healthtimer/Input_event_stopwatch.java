package com.example.healthtimer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class Input_event_stopwatch extends AppCompatActivity {
    private static final String TAG = "MyTag";
    DatabaseReference mDatabase;
    ArrayList<String> list_events = new ArrayList<>();
    ArrayList list_set = new ArrayList<>();
    ArrayList list_rest = new ArrayList<>();
    String events;
    Integer set, rest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_event_stopwatch);


        Spinner spinner_event = findViewById(R.id.spinner_event);
        Spinner spinner_set = findViewById(R.id.spinner_set);
        Spinner spinner_rest = findViewById(R.id.spinner_rest);
        Button btn_input = findViewById(R.id.btn_input);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_events);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_event.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Add_events");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuffer buffer = new StringBuffer();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot childDataSnapshot2 : childDataSnapshot.getChildren()) {

                        buffer.append(childDataSnapshot2.getValue());
                        list_events.add((String)childDataSnapshot2.getValue());
                        adapter.notifyDataSetChanged();
                    }
                    buffer.append("\n");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });





        spinner_event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                events = list_events.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_set.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position==0) set = 2;
               else if(position==1) set=3;
               else if(position==2) set=4;
               else if(position==3) set=5;
               else if(position==4) set=6;
               else if(position==5) set=7;
               else if(position==6) set=8;
               else if(position==7) set=9;
               else if(position==8) set=10;
               else if(position==9) set=11;
               else if(position==10) set=12;
               else if(position==11) set=13;
               else if(position==12) set=14;
               else if(position==13) set=15;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_rest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) rest =1;
                else if(position==1) rest=2;
                else if(position==2) rest=3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DB_Stopwatch.class);
                intent.putExtra("이벤트",events);
                intent.putExtra("세트",set);
                intent.putExtra("휴식",rest);
                StartToast(events+"선택됨");
                startActivity(intent);
                finish();
            }
        });



        }

    private void StartToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


 }

