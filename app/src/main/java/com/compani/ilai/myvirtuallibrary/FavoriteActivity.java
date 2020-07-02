package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    public static final String PARENT_FAVORITE_BOOKS = "favoriteBooks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView recyclerView = findViewById(R.id.favoriteRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, PARENT_FAVORITE_BOOKS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper db = new DatabaseHelper(FavoriteActivity.this);
        ArrayList<Book> favoriteList = db.getFavoriteBooks();
        adapter.setBooks(favoriteList);
    }
}