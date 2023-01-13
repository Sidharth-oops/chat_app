package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class chatactivity extends AppCompatActivity {
     Button b;
     EditText msg;
     ArrayList<message>messagelist;
     String receiverroom=null;
     String senderrrom=null;
     DatabaseReference mdbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
       mdbref=FirebaseDatabase.getInstance().getReference();
        RecyclerView chatrecycler=findViewById(R.id.chatreycler);
        b=findViewById(R.id.sendbutton);
        msg=findViewById(R.id.msg);
        chatrecycler.setLayoutManager(new LinearLayoutManager(this));
        messagelist=new ArrayList<>();
        RecyclerMessageAdapter adapter=new RecyclerMessageAdapter(this,messagelist);
       chatrecycler.setAdapter(adapter);


        Intent intent=getIntent();
        String receiveruid=intent.getStringExtra("uid");
        String senderuid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name=intent.getStringExtra("name");
        String k="";


        for(int i=0;i<name.length();i++){
            if(name.charAt(i)=='@'){
                break;
            }
            else{
                k=k+name.charAt(i);
            }
        }
        String h=k.toUpperCase(Locale.ROOT);
        getSupportActionBar().setTitle(h);

        senderrrom=receiveruid+senderuid;
        receiverroom=senderuid+receiveruid;
        mdbref.child("chats").child(senderrrom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagelist.clear();
                for(DataSnapshot postsnapshot :snapshot.getChildren()){
                    message m=postsnapshot.getValue(message.class);
                    messagelist.add(m);

                }
                adapter.notifyDataSetChanged();
                chatrecycler.scrollToPosition(adapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentmessage=msg.getText().toString();
                message m=new message(currentmessage,senderuid);
                mdbref.child("chats").child(senderrrom).child("messages").push().setValue(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mdbref.child("chats").child(receiverroom).child("messages").push().setValue(m);
                    }

                });
                msg.setText("");

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.logout_menu:{
                SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(chatactivity.this,LoginActivity.class));
            }
        }
        return true;
    }
}