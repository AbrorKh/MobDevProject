package com.example.sbapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaCas;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SessionPage extends AppCompatActivity {
    private static final String TAG = "SessionPage";
    private FirebaseFirestore db;
    private Map<String, Object> sessionsMap = new HashMap<String, Object>();
    ListView sessionsLV;
    ArrayList<Sessions> sessionsArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_page);

        db = FirebaseFirestore.getInstance();

        sessionsLV = findViewById(R.id.list_sessions);

        sessionsArrayList = new ArrayList<>();
        Toast.makeText(getApplicationContext(), "Fetching sessions...", Toast.LENGTH_SHORT).show();
        loadDatainListview();

    }

    private void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("Sessions").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Sessions sessionModal = d.toObject(Sessions.class);

                                // after getting data from Firebase we are
                                // storing that data in our array list
                                sessionsArrayList.add(sessionModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            SessionsLVAdapter adapter = new SessionsLVAdapter(SessionPage.this, sessionsArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            sessionsLV.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(SessionPage.this, "No session data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(SessionPage.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.session_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_session:
                final EditText SessionEditText = new EditText(this);
                final EditText timeEditText = new EditText(this);
                final EditText locEditText = new EditText(this);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new session")
                        .setMessage("Session details:")
                        .setView(SessionEditText)
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog builderTime = new AlertDialog.Builder(SessionPage.this)
                                        .setTitle("Set the time for the session")
                                        .setMessage("Time:")
                                        .setView(timeEditText)
                                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AlertDialog builderTime = new AlertDialog.Builder(SessionPage.this)
                                                        .setTitle("Set the location for the session")
                                                        .setMessage("Location:")
                                                        .setView(locEditText)
                                                        .setPositiveButton("Add session", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                String session = String.valueOf(SessionEditText.getText());
                                                                String time = String.valueOf(timeEditText.getText());
                                                                String location = String.valueOf(locEditText.getText());
                                                                sessionsMap.put("session", session);
                                                                sessionsMap.put("time", time);
                                                                sessionsMap.put("location", location);

                                                                db.collection("Sessions").document().set(sessionsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            sessionsMap.clear();
                                                                            Toast.makeText(SessionPage.this, "Session proposal created!", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }).setNegativeButton("Cancel", null)
                                                        .create();
                                                builderTime.show();
                                            }
                                        }).setNegativeButton("Cancel", null)
                                        .create();
                                builderTime.show();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SessionPage.this, HomePage.class);
        startActivity(intent);
        finish();
    }
}
