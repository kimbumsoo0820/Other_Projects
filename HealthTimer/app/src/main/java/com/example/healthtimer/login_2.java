package com.example.healthtimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login_2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "SignUpActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_2);


// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        findViewById(R.id.button_gotosignup).setOnClickListener(onClickListener);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.loginButton:
                    Log.e("클릭", "클릭");
                    Login();
                    break;
                case R.id.button_gotosignup:
                    MovetoSignup();
                    break;

            }
        }
    };
    private void Login() {

        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditTExt)).getText().toString();

        if(email.length() >0 && password.length() >0 ){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                StartToast("로그인 성공");
                                MovetoMain();
                            } else {


                                if(task.getException() != null)
                                    // If sign in fails, display a message to the user.
                                    StartToast(task.getException().toString());
                            }
                        }
                    });




        }
        else {
            StartToast("이메일 또는 비밀번호를 입력해주세요");
        }
    }

    private void StartToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void MovetoMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void MovetoSignup(){
        Intent intent = new Intent(this, Signup_2.class);
        startActivity(intent);
    }


}
