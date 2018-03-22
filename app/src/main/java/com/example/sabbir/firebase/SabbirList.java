package com.example.sabbir.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class SabbirList extends ArrayAdapter<Sabbir>{


    private Activity context;
    private List<Sabbir> sabbirList;


    public SabbirList(Activity context, List<Sabbir> sabbirList) {
        super(context, R.layout.list_layout, sabbirList);

        this.context = context;
        this.sabbirList = sabbirList;
    }



    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName =listViewItem.findViewById(R.id.Name);
        TextView textViewPhone =listViewItem.findViewById(R.id.phone);
        TextView userid =listViewItem.findViewById(R.id.Textvewid);


        Sabbir sabbir = sabbirList.get(position);


        textViewName.setText(sabbir.getSabbirGame());
        textViewPhone.setText(sabbir.getSabbirid());

        userid.setText(" user id : "+sabbir.getid());

        return listViewItem;

    }
}
