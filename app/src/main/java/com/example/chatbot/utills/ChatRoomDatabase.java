package com.example.chatbot.utills;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chatbot.Data.ChatDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ChatRoomDatabase extends RoomDatabase {

    public static final int NO_OF_THREADS = 4;
    private static ChatRoomDatabase INSTANCE;

    public abstract ChatDao chatDao();
    public static Executor databaseWriter = Executors.newFixedThreadPool(NO_OF_THREADS);

    public static ChatRoomDatabase getDatabase(final Context context){

        if( INSTANCE == null ){
            synchronized (ChatRoomDatabase.class){
                if (INSTANCE == null) {

//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            ChatRoomDatabase.class,"ChatDatabase")
//                            .addCallback(sRoomDatabaseCallBack)
//                            .build();

                }
                }
        }
        return INSTANCE;
        }


        private RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback(){

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                databaseWriter.execute(()->{

                    ChatDao chatDao = INSTANCE.chatDao();
                    chatDao.deleteAll();

                });

            }
        };


    }



