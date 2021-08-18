package com.example.android.roompost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    ArrayList<Post> data = new ArrayList<>();


    public void setPostsList(ArrayList<Post> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_items, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Post items = data.get(position);
        holder.bind(items.getTitle(),items.getBody());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView bodyTextView;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTextView = itemView.findViewById(R.id.title_textView3);
            this.bodyTextView = itemView.findViewById(R.id.body_textView4);
        }

        void bind(String title , String body) {
            titleTextView.setText(title);
            bodyTextView.setText(body);
        }

    }
}


