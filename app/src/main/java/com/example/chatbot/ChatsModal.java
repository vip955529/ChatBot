package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_table")
public class ChatsModal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String message;

    @NonNull
    private String sender;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChatsModal(@NonNull String message, @NonNull String sender) {
        this.message = message;
        this.sender = sender;
    }

}
