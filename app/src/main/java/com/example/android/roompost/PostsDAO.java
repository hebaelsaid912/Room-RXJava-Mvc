package com.example.android.roompost;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface PostsDAO {

    @Insert
    Completable insertPost(Post post);

    @Query("select * from posts_table")
    Single<List<Post>> getPosts();
}
