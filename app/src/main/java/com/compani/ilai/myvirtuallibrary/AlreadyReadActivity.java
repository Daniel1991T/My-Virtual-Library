package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class AlreadyReadActivity extends AppCompatActivity {

    public static final String PARENT_ALREADY_READ = "alreadyRead";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read);

        RecyclerView recyclerView = findViewById(R.id.alreadyReadRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(AlreadyReadActivity.this, PARENT_ALREADY_READ);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper db = new DatabaseHelper(AlreadyReadActivity.this);
        ArrayList<Book> alreadyReadList = db.getAlreadyReadBooks();

        adapter.setBooks(alreadyReadList);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}