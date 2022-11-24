package com.example.chatbot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot.Adaptor.ChatRVAdapter;
import com.example.chatbot.Data.RetrofitAPI;
import com.example.chatbot.Model.MsgModel;
import com.example.chatbot.utills.Constants;
import com.example.chatbot.utills.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        chatsRV = view.findViewById(R.id.idRVChats);
        userMsgEdt = view.findViewById(R.id.idEdtMessage);
        sendFAB = view.findViewById(R.id.idFABSend);
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);




TextView t1 = view.findViewById(R.id.t1);
t1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getResponse(t1.getText().toString());
    }
});TextView t2 = view.findViewById(R.id.t2);
t2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getResponse(t2.getText().toString());
    }
});TextView t3 = view.findViewById(R.id.t3);
t3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getResponse(t3.getText().toString());
    }
});
        sendFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter your message",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });
        return view;
    }

    private void getResponse(String message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url ="http://api.brainshop.ai/get?bid=170520&key=iKuo2DvNLeLuAcC8&uid=[uid]&msg="+message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModel> call = retrofitAPI.getMessage(url);

        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if(response.isSuccessful()){
                    MsgModel model= response.body();
                    chatsModalArrayList.add(new ChatsModal(model.getCnt(),BOT_KEY));

                    PreferenceManager preferenceManager = new PreferenceManager(Constants.USER,getContext());


                    ChatsModal chatsModal = new ChatsModal(model.getCnt(),BOT_KEY);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("chats").child(preferenceManager.getString(Constants.PHONE));
                    String id = myRef.push().getKey();

                    myRef.child(id).setValue(chatsModal);


                 //   chatsModalArrayList.add(new ChatsModal(model.getCnt(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();

            }
        });
    }
}