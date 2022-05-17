package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ChatsRV;
    private EditText userMsgEdit;
    private FloatingActionButton sendMessageFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<chatsModal>chatsModalArrayList;
    private chatRVAdapter chatRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatsRV = findViewById(R.id.idRVChats);
        userMsgEdit = findViewById(R.id.idEditMessage);
        sendMessageFAB = findViewById(R.id.idFABSend);
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new chatRVAdapter(chatsModalArrayList, this );
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ChatsRV.setLayoutManager(manager);
        ChatsRV.setAdapter(chatRVAdapter);
        sendMessageFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMsgEdit.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdit.getText().toString());
                userMsgEdit.setText("");
            }
        });
        
    }
    private void getResponse(String message)
    {
        chatsModalArrayList.add(new chatsModal(message, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=166525&key=fbscBfSpgo7BkI9q&uid=[uid]&msg="+message;
        String Base_url = "http://api.brainshop.ai/";
         Retrofit retrofit = new Retrofit.Builder().baseUrl(Base_url).addConverterFactory(GsonConverterFactory.create()).build();
         RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call = retrofitAPI.getMessage(url);
       call.enqueue(new Callback<MsgModal>() {
           @Override
           public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
               if(response.isSuccessful())
               {
                   MsgModal modal = response.body();
                   chatsModalArrayList.add(new chatsModal(modal.getCnt(), BOT_KEY));
                   chatRVAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new chatsModal("Please revert your question", BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
           }
       });

    }
}