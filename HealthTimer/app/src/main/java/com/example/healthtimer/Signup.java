package com.example.healthtimer;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    EditText signup_email,signup_pw,signup_pw_ch;
    Button email_button,google_button,facebook_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signup_email = findViewById(R.id.signup_email);
        signup_pw = findViewById(R.id.signup_pw);
        signup_pw_ch = findViewById(R.id.signup_pw_ch);
        email_button = findViewById(R.id.email_button);


        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signup_email.getText().toString();
                String pw = signup_pw.getText().toString();
                String pw_ch = signup_pw_ch.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    signup_email.setError("이메일을 입력하세요.");
                }if(TextUtils.isEmpty(pw)) {
                    signup_pw.setError("비밀번호를 입력하세요.");
                }if(pw.length() < 8){
                    signup_pw.setError("비밀번호는 최소 8자 이상입니다.");
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Signup.this, "완료", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signup.this,MainActivity.class));
                            }else {
                                Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }




            }
        });




    }
}