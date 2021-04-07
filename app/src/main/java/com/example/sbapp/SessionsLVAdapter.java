package com.example.sbapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SessionsLVAdapter extends ArrayAdapter<Sessions> {
    // constructor for our list view adapter.
    public SessionsLVAdapter(@NonNull Context context, ArrayList<Sessions> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_session, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Sessions dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView name = listitemView.findViewById(R.id.idsname);
        TextView time = listitemView.findViewById(R.id.idstime);
        TextView location = listitemView.findViewById(R.id.idsloc);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        name.setText(dataModal.getSession());
        time.setText(dataModal.getTime());
        location.setText(dataModal.getLocation());

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Session: " + dataModal.getSession(), Toast.LENGTH_SHORT).show();

                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + dataModal.getLocation());

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                    mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                    v.getContext().startActivity(mapIntent);




            }
        });
        return listitemView;
    }
}
