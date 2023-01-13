package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    public TextView geeksforgeeks;
    public Button logout,move;
    public EditText et;
    public DatabaseReference mdbref;
    public String value="";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geeksforgeeks = (TextView) findViewById(R.id.hii);
        logout=findViewById(R.id.out);
        move=findViewById(R.id.move);



        SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
        String email=sharedPreferences.getString("useremail",String.valueOf(MODE_PRIVATE));
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)=='@'){
                break;
            }
            else{
                value=value+email.charAt(i);
            }
        }

    String t=value.toUpperCase(Locale.ROOT);
        geeksforgeeks.setText("LOGGED IN AS "+t);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,chat.class));
            }
        });





    }
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }




}