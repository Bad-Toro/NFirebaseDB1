package com.mobileappscompany.training.nfirebasedb1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tV;
    EditText eT;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Person mP;
    List<Person> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tV = (TextView) findViewById(R.id.textV);
        eT = (EditText) findViewById(R.id.editText);

       database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        mP = new Person("Joe");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) return;

                people.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Person iP = ds.getValue(Person.class);
                    people.add(iP);
                }

                tV.setText(people.get(people.size()-1).getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    void doDB() {

        mP.setName(eT.getText().toString());

        String k = myRef.push().getKey();

        myRef.child(k).setValue(mP);
        //myRef.setValue(mP);

    }

    public void onDODB(View view) {
        doDB();
    }
}
