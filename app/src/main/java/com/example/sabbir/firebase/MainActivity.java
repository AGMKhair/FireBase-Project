package com.example.sabbir.firebase;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    public static final String SABBIR_NAME = "sabbirname";
    public static final String SABBIR_ID = "sabbirId";

    EditText name, id, phone;
    Button submit,showdata;

    String user, username, userphone ,useremail;
    //FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listviewSabbir;
    List<Sabbir> sabbirlist;

    TextView textView ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("empty");

        //textView = findViewById(R.id.viewSnapshot);


        name = findViewById(R.id.name);
        id = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        submit = findViewById(R.id.addButton);
        showdata = findViewById(R.id.showbutton);
        listviewSabbir = findViewById(R.id.listViewSabbir);


        sabbirlist = new ArrayList<>();



        listviewSabbir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Sabbir sabbir = sabbirlist.get(position);

                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);

                intent.putExtra(SABBIR_ID, sabbir.getSabbirid());
                intent.putExtra(SABBIR_NAME, sabbir.getSabbirName());


                startActivity(intent);
            }
        });

        listviewSabbir.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Sabbir sabbir = sabbirlist.get(position);

                showUpdatedialog(sabbir.getSabbirid(),sabbir.getSabbirName());

                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                user = id.getText().toString().trim();
                username = name.getText().toString().trim();
                userphone = phone.getText().toString().trim();

                //ArrayList<String> n = new ArrayList<>();



                if(!TextUtils.isEmpty(user))
                {

                    //String id = myRef.push().getKey();

                    Sabbir sabbir = new Sabbir(user,username,userphone);

                     //myRef.child(user).child(userphone).setValue(useremail ,user);
                //    DataSnapshot d;


                    //d.exists();

                    myRef.child(user).setValue(sabbir);


                    Toast.makeText(MainActivity.this," complete ", Toast.LENGTH_SHORT).show();



                }
                //methd();


            }
        });

        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methd();
            }
        });

    }



    private void showUpdatedialog(final String sabbirid, String sabbirName)
    {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog,null);

        dialogbuilder.setView(dialogView);


        final TextView textViewName = dialogView.findViewById(R.id.textviewname);
        final EditText editText = dialogView.findViewById(R.id.edittext);
        final Button buttonupdate = dialogView.findViewById(R.id.buttonupdata);
        final Button buttondelete = dialogView.findViewById(R.id.buttonDelete);

        dialogbuilder.setTitle("Updating Sabbir......  "+ sabbirid);

        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();




        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                String gene = textViewName.getText().toString().trim();

                if(TextUtils.isEmpty(name))
                {
                    editText.setError(" Name required" );


                    return;



                }


                updatesabbir(sabbirid,name,gene);


                alertDialog.dismiss();
            }
        });


        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletemethod(sabbirid);

                alertDialog.dismiss();
            }
        });



    }

    private void deletemethod(String sabbirid) {

        DatabaseReference dtsabbirid = FirebaseDatabase.getInstance().getReference("empty").child(sabbirid);
        DatabaseReference dtsabbirtrack = FirebaseDatabase.getInstance().getReference("track").child(sabbirid);

        dtsabbirid.removeValue();
        dtsabbirtrack.removeValue();
    }


    private boolean updatesabbir(String id , String name, String genre)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("empty").child(id);

        Sabbir sabbir = new Sabbir(id,name, genre);
        databaseReference.setValue(sabbir);

        Toast.makeText(MainActivity.this, " complete data update ",Toast.LENGTH_SHORT).show();

        return true;

    }



    @Override
    protected void onStart() {
        super.onStart();
        methd();
    }

    private void methd() {



        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                sabbirlist.clear();


/*
                String s =  dataSnapshot.child(user).child(userphone).getValue(String.class);
                textView.setText(s);*/

                for (DataSnapshot sabbirSnapshot : dataSnapshot.getChildren())
                {

                    /*

                    Sabbir sabbir = sabbirSnapshot.child(user).child(userphone).getValue(Sabbir.class);

                    sabbirlist.add(sabbir);*/
           /*      String s =  sabbirSnapshot.getValue().toString();
                 textView.setText(s);*/

                //sabbirSnapshot.getValue();


                    Sabbir sabbir = sabbirSnapshot.getValue(Sabbir.class);

                    sabbirlist.add(sabbir);


                }






                SabbirList adapter = new SabbirList(MainActivity.this,sabbirlist);

                listviewSabbir.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


       // Toast.makeText(MainActivity.this,"this is the submited value", Toast.LENGTH_SHORT).show();

    }
