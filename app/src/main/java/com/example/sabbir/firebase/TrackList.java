package com.example.sabbir.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sabbir on 3/22/2018.
 */



public class TrackList extends ArrayAdapter<Track> {


    private Activity context;
    private List<Track> tracklist;


    public TrackList(Activity context, List<Track> tracklist) {
        super(context, R.layout.layout_track_list, tracklist);

        this.context = context;
        this.tracklist = tracklist;
    }



    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_track_list, null, true);

        TextView textViewName =listViewItem.findViewById(R.id.TrackName);
        TextView textViewPhone =listViewItem.findViewById(R.id.Trackphone);
        TextView userid =listViewItem.findViewById(R.id.TrackTextvewid);


        Track track = tracklist.get(position);


        textViewName.setText(track.getTrackId());
        textViewPhone.setText(track.getTrackName());

        userid.setText(" Rating : "+ track.getTrackRating());

        return listViewItem;

    }
}
