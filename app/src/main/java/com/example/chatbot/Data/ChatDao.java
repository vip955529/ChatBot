package com.example.chatbot.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatbot.ChatsModal;

import java.util.List;

@Dao
public interface ChatDao {

    // C-creation,R-read,U-update,D-delete

    @Insert
    public void insert(ChatsModal chatsModal);

    @Query("SELECT * FROM CHAT_TABLE")
    public LiveData<List<ChatsModal>> getAllChats();

    @Query("DELETE FROM CHAT_TABLE")
    public void deleteAll();

}
