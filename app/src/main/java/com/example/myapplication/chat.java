package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat extends AppCompatActivity {

      private RecyclerView recyclerView;
    private ArrayList<user> users;
      private FirebaseAuth mauth;
    private  DatabaseReference mdref;
    private FirebaseUser firebaseUser;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropdown);

        recyclerView=findViewById(R.id.recycler);
        mdref=FirebaseDatabase.getInstance().getReference().child("USER");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users=new ArrayList<>();

        RecyclerContactAdapter adapter=new RecyclerContactAdapter(this,users);
        recyclerView.setAdapter(adapter);


        mdref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        user friend = (user) ds.getValue(user.class);
                        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                        String p=firebaseUser.getEmail();
                        String k="";


                        for(int i=0;i<p.length();i++){
                            if(p.charAt(i)=='@'){
                                break;
                            }
                            else{
                                k=k+p.charAt(i);
                            }
                        }

                        if(k.equals(friend.name)){
                            continue;
                        }
                        else{
                            users.add(friend);

                        }





                    }

                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}