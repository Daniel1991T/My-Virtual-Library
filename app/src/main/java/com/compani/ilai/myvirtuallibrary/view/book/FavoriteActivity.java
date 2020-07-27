package com.compani.ilai.myvirtuallibrary.view.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.compani.ilai.myvirtuallibrary.services.Book;
import com.compani.ilai.myvirtuallibrary.services.BookRecViewAdapter;
import com.compani.ilai.myvirtuallibrary.MainActivity;
import com.compani.ilai.myvirtuallibrary.R;
import com.compani.ilai.myvirtuallibrary.repository.FavoriteRepository;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    public static final String PARENT_FAVORITE_BOOKS = "favoriteBooks";
    private FavoriteRepository mFavoriteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mFavoriteRepository = new FavoriteRepository(this);

        RecyclerView recyclerView = findViewById(R.id.favoriteRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, PARENT_FAVORITE_BOOKS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Book> favoriteList = new ArrayList<>(mFavoriteRepository.getBooksList());
        adapter.setBooks(favoriteList);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}