package com.compani.ilai.myvirtuallibrary.view.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.compani.ilai.myvirtuallibrary.services.AddBookActivity;
import com.compani.ilai.myvirtuallibrary.services.Book;
import com.compani.ilai.myvirtuallibrary.services.BookRecViewAdapter;
import com.compani.ilai.myvirtuallibrary.MainActivity;
import com.compani.ilai.myvirtuallibrary.R;
import com.compani.ilai.myvirtuallibrary.repository.AllBookRepository;
import com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class AllBooksActivity extends AppCompatActivity {

    private static final String TAG = "AllBooksActivity";

    public static final String PARENT_ALL_BOOKS = "allBooks";
    private RecyclerView bookRecView;
    private BookRecViewAdapter adapterBook;
    private FloatingActionButton addBook;
    private Spinner mSpinner;
    private String selectedGenres = "All Genres";
    private AllBookRepository mAllBookRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        mAllBookRepository = new AllBookRepository(this);


        adapterBook = new BookRecViewAdapter(this, PARENT_ALL_BOOKS);
        bookRecView = findViewById(R.id.allBookRecView);
        addBook = findViewById(R.id.fabAddBook);

        bookRecView.setAdapter(adapterBook);
        bookRecView.setLayoutManager(new LinearLayoutManager(this));

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllBooksActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        mSpinner = findViewById(R.id.allBookSpinner);
        ArrayList<String> genresList = new ArrayList<>();
        genresList.add("All Genres");
        genresList.addAll(mAllBookRepository.getGenresList());
        mSpinner.setAdapter(new ArrayAdapter<>(AllBooksActivity.this,
                R.layout.spinner_drop_down_item, genresList));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenres = parent.getItemAtPosition(position).toString();
                adapterBook.getFilter().filter(selectedGenres);
                Log.d(TAG, "onItemSelected: called");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGenres = "All Genres";
                Log.d(TAG, "onNothingSelected: called");
            }
        });

        if (selectedGenres.equals("All Genres")) {
            ArrayList<Book> books = new ArrayList<>(mAllBookRepository.getBooksList());
            adapterBook.setBooks(books);
        } else {
            adapterBook.getFilter().filter(selectedGenres);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}