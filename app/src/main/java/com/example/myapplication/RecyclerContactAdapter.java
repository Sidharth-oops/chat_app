package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {
    Context context;
    ArrayList<user>arraycontact;


    public RecyclerContactAdapter(Context context, ArrayList<user>arraycontact){
        this.context=context;
        this.arraycontact=arraycontact;

    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.activity_chat,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id=arraycontact.get(position).uid;
        String temp=arraycontact.get(position).name;
        String t=temp.toUpperCase(Locale.ROOT);
        holder.text.setText(t);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(context,
                        chatactivity.class);


                intent.putExtra("uid",id);
                intent.putExtra("name",temp);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraycontact.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.chat);
        }


    }


}
