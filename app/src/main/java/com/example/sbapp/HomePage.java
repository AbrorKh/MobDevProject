package com.example.sbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    private Button button;
    private ImageView chat;
    private ImageView sessions;
    private TextView chatText;
    private TextView sessionsText;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.buttonLogout);
        chat = findViewById(R.id.chatIco);
        sessions = findViewById(R.id.sessionIco);
        chatText = findViewById(R.id.chatText);
        sessionsText = findViewById(R.id.sessionText);



//subtitles onclicklisteners
        chatText.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, ChatPage.class));
            finish();
//            Toast.makeText(HomePage.this,"Communications feature will be enabled soon...", Toast.LENGTH_SHORT).show();
        });

        sessionsText.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, SessionPage.class));
            finish();
//            Toast.makeText(HomePage.this,"Sessions feature will be enabled soon...", Toast.LENGTH_SHORT).show();
        });
        //icons onclicklisteners
        chat.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, ChatPage.class));
            finish();
//            Toast.makeText(HomePage.this,"Communications feature will be enabled soon...", Toast.LENGTH_SHORT).show();
        });

        sessions.setOnClickListener(v -> {
            startActivity(new Intent(HomePage.this, SessionPage.class));
            finish();
//            Toast.makeText(HomePage.this,"Sessions feature will be enabled soon...", Toast.LENGTH_SHORT).show();
        });

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(HomePage.this, "Logged out \uD83D\uDC4B.\nSee you next time...", Toast.LENGTH_SHORT);
            startActivity(new Intent(HomePage.this, MainActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(HomePage.this, AboutPage.class);
                startActivity(intent);
                finish();
                return true;
//            case R.id.student:
//                Intent intent1 = new Intent(HomePage.this, StudentPage.class);
//                startActivity(intent1);
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}