package com.example.android.roompost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView posts_rv;
    private Button insert_btn;
    private Button getPosts_btn;
    private EditText title_et;
    private EditText body_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts_rv = findViewById(R.id.showPostsRV);
        PostsAdapter postsAdapter = new PostsAdapter();
        posts_rv.setAdapter(postsAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        posts_rv.setLayoutManager(mLayoutManager);

        insert_btn = findViewById(R.id.insertPostbutton);
        getPosts_btn = findViewById(R.id.getPostsbutton2);

        title_et = findViewById(R.id.title_et);
        body_et = findViewById(R.id.body_et);

        PostsDatabase postsDatabase = PostsDatabase.getINSTANCE(this);


       // postsDatabase.postsDAO().insertPost(new Post(2,"room database" , "this is first to use room database in android"));
        //لو عملت رن هنا هيحصل كراش عشان مفيش اكسس ان الداتابيز تشتغل ع ال main Thread
        //الحل ان نستخدم rxJava
        // عشان مقدرش اشتغل الداتا بيز غير ف الباكراوند وانا كده كنت بشغلها ف ال ui thread

        //with rxJava

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postsDatabase.postsDAO().insertPost(new Post(2,title_et.getText().toString().trim() ,
                        body_et.getText().toString().trim()))
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Log.d("insert","added successfully");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("insert",e.getMessage());
                            }
                        });
            }
        });
        getPosts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postsDatabase.postsDAO().getPosts()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<Post>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull List<Post> posts) {
                                postsAdapter.setPostsList((ArrayList<Post>) posts);
                                postsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
            }
        });




        

    }
}