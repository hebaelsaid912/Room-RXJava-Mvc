package com.example.android.roompost;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Post.class , version = 1)
abstract class PostsDatabase extends RoomDatabase {

   private static PostsDatabase INSTANCE;
   public abstract PostsDAO postsDAO();

   //single to pattern
   //why static ?
   // عشان تبقا شير للابلكيشن كله
   // why synchronized ?
   //عشان مفيش اكتر من thread تأكسس الفانكشن ف نفس الوقت
   public static synchronized PostsDatabase getINSTANCE(Context context) {
      if(INSTANCE == null){
         INSTANCE = Room.databaseBuilder(context,PostsDatabase.class,"posts_database")
                 .fallbackToDestructiveMigration()
                 .build();

      }
      return INSTANCE;
   }


}
