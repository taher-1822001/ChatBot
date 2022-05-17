package com.example.chatbot;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatRVAdapter extends RecyclerView.Adapter {
    private ArrayList<chatsModal> ChatsModalArrayList;
    private Context context;

    public chatRVAdapter(ArrayList<chatsModal> chatsModalArrayList, Context context) {
        ChatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
       switch (viewType)
       {
           case 0:
               view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item, parent, false);
               return new UserViewHolder(view);
           case 1:
               view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item, parent, false);
               return new UserViewHolder.BotViewHolder(view);
       }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        chatsModal chatsModal = ChatsModalArrayList.get(position);

        switch (chatsModal.getSender())
        {
            case "user":
                ((UserViewHolder)holder).userTV.setText(chatsModal.getMessage());
                break;
            case "bot":
                ((UserViewHolder.BotViewHolder)holder).BotMsgTV.setText(chatsModal.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (ChatsModalArrayList.get(position).getSender())
        {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return ChatsModalArrayList.size();
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.idTVUser);
        }

        public static class BotViewHolder extends RecyclerView.ViewHolder
        {
            TextView BotMsgTV;

            public BotViewHolder(@NonNull View itemView) {
                super(itemView);
                BotMsgTV = itemView.findViewById(R.id.idTVBot);
            }
        }
    }
}
