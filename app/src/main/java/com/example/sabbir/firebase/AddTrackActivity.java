package com.example.sabbir.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {


    TextView textView;
    EditText editText, editText2,editText3;

    SeekBar seekBar;

    DatabaseReference databaseTrack;


    ListView listViewTracks;
    Button buttonAddTrack;

    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

       // textView = findViewById(R.id.te)

        editText = findViewById(R.id.Trackuser);
        editText2 = findViewById(R.id.TrackName);
        editText3 = findViewById(R.id.Trackphone);
        buttonAddTrack = findViewById(R.id.AddTrack);

        seekBar = findViewById(R.id.seekBar);

        listViewTracks = findViewById(R.id.listViewTrack);

        Intent intent = getIntent();


        tracks = new ArrayList<>();
        String id = intent.getStringExtra(MainActivity.SABBIR_ID);
        String name = intent.getStringExtra(MainActivity.SABBIR_NAME);


        databaseTrack = FirebaseDatabase.getInstance().getReference("track").child(id);

        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTrack.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();

                for(DataSnapshot tracksnapshot : dataSnapshot.getChildren())
                {
                    Track track = tracksnapshot.getValue(Track.class);
                    tracks.add(track);
                }


                TrackList trackListAdapter = new TrackList(AddTrackActivity.this, tracks);

                listViewTracks.setAdapter(trackListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveTrack() {
        String trackid = editText.getText().toString().trim();
        String trackName = editText2.getText().toString().trim();
        int rating = seekBar.getProgress();

        if(!TextUtils.isEmpty(trackid))
        {
            Track track = new Track(trackid,trackName,rating);

            databaseTrack.child(trackid).setValue(track);

            Toast.makeText(AddTrackActivity.this," Success " , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(AddTrackActivity.this," faild " , Toast.LENGTH_SHORT).show();
        }



    }
}
