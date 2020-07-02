package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CurrentlyReadingActivity extends AppCompatActivity {

    public static final String PARENT_CURRENTLY_READING = "currentlyReading";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_reading);

        RecyclerView recyclerView = findViewById(R.id.currentlyReadRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, PARENT_CURRENTLY_READING);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHelper db = new DatabaseHelper(CurrentlyReadingActivity.this);
        ArrayList<Book> currentlyReadingBooks = db.getCurrentlyReadingBooks();
        adapter.setBooks(currentlyReadingBooks);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}