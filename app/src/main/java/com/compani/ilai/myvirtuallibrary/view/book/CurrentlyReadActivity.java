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
import com.compani.ilai.myvirtuallibrary.repository.CurrentlyReadRepository;

import java.util.ArrayList;

public class CurrentlyReadActivity extends AppCompatActivity {

    public static final String PARENT_CURRENTLY_READING = "currentlyReading";
    private CurrentlyReadRepository mCurrentlyReadRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_reading);
        mCurrentlyReadRepository = new CurrentlyReadRepository(this);

        RecyclerView recyclerView = findViewById(R.id.currentlyReadRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, PARENT_CURRENTLY_READING);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Book> currentlyReadingBooks = mCurrentlyReadRepository.getBooksList();
        adapter.setBooks(currentlyReadingBooks);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}