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
import com.compani.ilai.myvirtuallibrary.repository.WantToReadRepository;

import java.util.ArrayList;

public class WantToReadActivity extends AppCompatActivity {

    public static final String PARENT_WANT_TO_READ = "wantToRead";
    private WantToReadRepository mWantToReadRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_to_read);
        mWantToReadRepository = new WantToReadRepository(this);

        RecyclerView recyclerView = findViewById(R.id.wantToReadRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, PARENT_WANT_TO_READ);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Book> wantToRead = new ArrayList<>(mWantToReadRepository.getBooksList());
        adapter.setBooks(wantToRead);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}