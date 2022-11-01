package com.IN6222.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText mail;
    EditText password;
    Button signIn;
    Button signup;

    FirebaseAuth mAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        signIn=findViewById(R.id.buttonSignin);
        signup=findViewById(R.id.buttonSignup);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String userMail=mail.getText().toString();
//                String userPassword=password.getText().toString();
//
//                signInFirebase(userMail,userPassword);
//                Intent intent=new Intent(MainActivity.this,MainPage.class);
                Intent intent=new Intent(MainActivity.this,NavigationPage.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);

            }
        });
    }

    public void signInFirebase(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

}