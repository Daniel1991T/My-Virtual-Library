package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class AllBooksActivity extends AppCompatActivity {

    public static final String PARENT_ALL_BOOKS = "allBooks";
    private RecyclerView bookRecView;
    private BookRecViewAdapter adapter;
    private FloatingActionButton addBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        adapter = new BookRecViewAdapter(this, PARENT_ALL_BOOKS);
        bookRecView = findViewById(R.id.allBookRecView);
        addBook = findViewById(R.id.fabAddBook);

        bookRecView.setAdapter(adapter);
        bookRecView.setLayoutManager(new LinearLayoutManager(this));

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllBooksActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(AllBooksActivity.this);
        ArrayList<Book> books = new ArrayList<>(databaseHelper.getAllBooksList());
        adapter.setBooks(books);

    }
}