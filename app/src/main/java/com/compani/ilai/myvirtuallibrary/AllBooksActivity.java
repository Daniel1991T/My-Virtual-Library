package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



public class AllBooksActivity extends AppCompatActivity {

    private static final String TAG = "AllBooksActivity";

    public static final String PARENT_ALL_BOOKS = "allBooks";
    private RecyclerView bookRecView;
    private BookRecViewAdapter adapterBook;
    private FloatingActionButton addBook;
    private Spinner mSpinner;
    private String selectedGenres = "All Genres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

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

        DatabaseHelper databaseHelper = new DatabaseHelper(AllBooksActivity.this);
        mSpinner = findViewById(R.id.allBookSpinner);
         ArrayList<String> genresList = new ArrayList<>();
        genresList.add("All Genres");
        genresList.addAll(databaseHelper.getGenresList());
        mSpinner.setAdapter(new ArrayAdapter<>(AllBooksActivity.this,
                R.layout.spinner_drop_down_item, genresList));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenres = parent.getItemAtPosition(position).toString();
                adapterBook.getFilter().filter(selectedGenres);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGenres = "All Genres";
            }
        });

        if (selectedGenres.equals("All Genres")) {
            ArrayList<Book> books = new ArrayList<>(databaseHelper.getAllBooksList());
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