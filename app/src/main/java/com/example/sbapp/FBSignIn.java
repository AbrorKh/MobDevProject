package com.example.sbapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FBSignIn extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button button;
    private FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email = findViewById(R.id.sEditEmail);
        password = findViewById(R.id.sEditPassword);
        button = findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString();
                String passStr = password.getText().toString();

                userSignIn(emailStr, passStr);
            }
        });
    }

    private void userSignIn(String emailStr, String passStr) {
        auth.signInWithEmailAndPassword(emailStr, passStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           @Override
           public void onSuccess(AuthResult result) {
               Toast.makeText(FBSignIn.this, "Yay you signed in!!!", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(FBSignIn.this, HomePage.class));
               finish();
           }
        });
    }
}
