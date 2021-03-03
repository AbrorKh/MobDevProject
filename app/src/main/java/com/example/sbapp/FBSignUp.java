package com.example.sbapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class FBSignUp extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText fullName;
    private EditText status;
    private Button submit;
    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        fullName = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        status = findViewById(R.id.editStatus);
        submit = findViewById(R.id.submit);
//        Instantiate FB authentication
        auth = FirebaseAuth.getInstance();

//      submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();
                String nameInput = fullName.getText().toString();
                String statusInput = status.getText().toString();
                if(TextUtils.isEmpty(nameInput) || TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput) || TextUtils.isEmpty(statusInput)){
                    Toast.makeText(FBSignUp.this, "Enter all the fields!\uD83E\uDDD0\uD83E\uDDD0\uD83E\uDDD0", Toast.LENGTH_SHORT).show();
                } else if(passwordInput.length() < 8){
                    Toast.makeText(FBSignUp.this, "Password seems weak! \uD83D\uDE15\uD83D\uDE15\uD83D\uDE15 Try making at least an 8-character password.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        signUp(nameInput, emailInput, passwordInput, statusInput);
                    } catch (Exception e) {
                        System.out.println("Exception here when sign up: " + e);
                    }

                }
            }
        });

    }

    private void signUp(String nameInput, String emailInput, String passwordInput, String statusInput) {
        auth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(FBSignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(FBSignUp.this, "Yay, you signed up!!!\uD83D\uDE04\uD83D\uDE04\uD83D\uDE04 You can use the app now!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FBSignUp.this, HomePage.class));
                    finish();
                } else {
                    Toast.makeText(FBSignUp.this, "Couldn't signed up!!!Try again Please", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private static final String[] Options = new String[] {
//            "TA", "Tutor", "Student"
//    };

}
