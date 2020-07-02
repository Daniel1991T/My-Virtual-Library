package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class WantToReadActivity extends AppCompatActivity {

    public static final String PARENT_WANT_TO_READ = "wantToRead";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_read);

        RecyclerView recyclerView = findViewById(R.id.wantToReadRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, PARENT_WANT_TO_READ);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<Book> wantToRead = databaseHelper.getWantToReadBooks();
        adapter.setBooks(wantToRead);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}