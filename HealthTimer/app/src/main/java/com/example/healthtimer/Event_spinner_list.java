package com.example.healthtimer;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Event_spinner_list extends AppCompatActivity {
    DatabaseReference mDatabase,nDatabase;
    ArrayList<String> list_events = new ArrayList<>();
    ArrayList<String> list_time = new ArrayList<>();
    Integer btn_flag=0;


    String events;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_spinner_list);

        Spinner spinner_event = findViewById(R.id.spinner_event);
        Button btn_list_search = findViewById(R.id.btn_list_search);
        TextView txt_event_list = (TextView)findViewById(R.id.txt_event_list);

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
                        list_events.add((String) childDataSnapshot2.getValue());
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
                txt_event_list.setText("");
                list_time.clear();
                btn_flag=0;
                btn_list_search.setEnabled(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btn_list_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_flag++;
                if(btn_flag>=2){
                    btn_list_search.setEnabled(false);
                }else{
                    btn_list_search.setEnabled(true);
                }

                nDatabase = FirebaseDatabase.getInstance().getReference().child(events);

                nDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        StringBuffer buffer = new StringBuffer();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                                buffer.append(childDataSnapshot.getValue());
                                list_time.add((String) childDataSnapshot.getValue());
                                list_time.add("\n");
                                adapter.notifyDataSetChanged();


                        }buffer.append("\n");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                txt_event_list.setText(list_time+"\n");
            }

        });

    }
}

