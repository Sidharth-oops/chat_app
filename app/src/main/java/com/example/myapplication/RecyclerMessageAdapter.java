package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RecyclerMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<message> arraymessage;
    int ITEM_SENT=1;
    int ITEM_RECEIVE=2;
    public RecyclerMessageAdapter(Context context, ArrayList<message>arraymessage){
        this.context=context;
        this.arraymessage=arraymessage;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           if(viewType==1){
               View v= LayoutInflater.from(context).inflate(R.layout.sent,parent,false);
              return new SentViewHolder(v);

           }
           else{
               View v= LayoutInflater.from(context).inflate(R.layout.receive,parent,false);
               return new RecieveViewHolder(v);

           }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          message m=arraymessage.get(position);
           if(holder.getClass()==SentViewHolder.class){
              SentViewHolder sentViewHolder=(SentViewHolder) holder;
              sentViewHolder.send.setText(m.message);

           }
           else{
                RecieveViewHolder recieveViewHolder=(RecieveViewHolder)  holder;
                recieveViewHolder.receive.setText(m.message);
           }
    }
    public  int getItemViewType(int position){
        message m=arraymessage.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(m.senderid)){
            return ITEM_SENT;
        }
        else{
            return ITEM_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return arraymessage.size();
    }
    public  class SentViewHolder extends RecyclerView.ViewHolder {
        TextView send;
        public SentViewHolder(View itemView) {
            super(itemView);
             send=itemView.findViewById(R.id.sentMessage);
        }


    }
    public  class RecieveViewHolder extends RecyclerView.ViewHolder {
        TextView receive;
        public RecieveViewHolder(View itemView) {
            super(itemView);
            receive=itemView.findViewById(R.id.receive_message);

        }


    }
}
