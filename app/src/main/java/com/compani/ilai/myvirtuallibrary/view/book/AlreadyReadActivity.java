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
import com.compani.ilai.myvirtuallibrary.repository.AlreadyReadRepository;

import java.util.ArrayList;

public class AlreadyReadActivity extends AppCompatActivity {

    public static final String PARENT_ALREADY_READ = "alreadyRead";
    private AlreadyReadRepository mAlreadyReadRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read);
        mAlreadyReadRepository = new AlreadyReadRepository(this);

        RecyclerView recyclerView = findViewById(R.id.alreadyReadRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(AlreadyReadActivity.this, PARENT_ALREADY_READ);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Book> alreadyReadList = new ArrayList<>(mAlreadyReadRepository.getBooksList());

        adapter.setBooks(alreadyReadList);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}