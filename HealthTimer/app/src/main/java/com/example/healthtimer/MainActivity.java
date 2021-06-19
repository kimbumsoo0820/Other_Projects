package com.example.healthtimer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import activity.BasicActivity;

public class MainActivity extends BasicActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_menu);
        Button menu_normal_timer = findViewById(R.id.menu_normal_timer);
        Button logout = findViewById(R.id.logout);
        Button menu_add = findViewById(R.id.menu_add);
        Button menu_set_timer = findViewById(R.id.menu_set_timer);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        menu_normal_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Normal_Stopwatch.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Add_event_main.class);
                startActivity(intent);

            }
        });

        menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Event_spinner_list.class);
                startActivity(intent);

            }
        });

        menu_set_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Input_event_stopwatch.class);
                startActivity(intent);
            }
        });
















    }



    private void StartToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }






}