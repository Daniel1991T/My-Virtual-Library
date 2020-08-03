package com.compani.ilai.myvirtuallibrary.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.compani.ilai.myvirtuallibrary.R;
import com.compani.ilai.myvirtuallibrary.repository.AllBookRepository;
import com.compani.ilai.myvirtuallibrary.view.book.AllBooksActivity;

public class AddBookActivity extends AppCompatActivity {

    private Button btnAddBook;
    private EditText eTxtName, eTxtAuthor, eTxtPages, eTxtGen, eTxtImageUrl, eTxtDescription;
    private AllBookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initView();
        bookRepository = new AllBookRepository(this);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setBook()) {
                    Toast.makeText(AddBookActivity.this, "Book successful add!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddBookActivity.this, AllBooksActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddBookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean setBook() {
        String name = eTxtName.getText().toString().trim();
        String author = eTxtAuthor.getText().toString().trim();
        int pages = Integer.parseInt(eTxtPages.getText().toString().trim());
        String urlImage = eTxtImageUrl.getText().toString().trim();
        String description = eTxtDescription.getText().toString().trim();
        String gen = eTxtGen.getText().toString().trim();
        Book book = new Book(-1, name, author, pages, gen, urlImage, description);

        if (name.equals("") || author.equals("")) {
            Toast.makeText(this, "Need completed all fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return bookRepository.addBook(book);
    }

    private void initView() {
        btnAddBook = findViewById(R.id.addBookbtn);

        eTxtName = findViewById(R.id.addBookName);
        eTxtAuthor = findViewById(R.id.addBookAuthor);
        eTxtGen = findViewById(R.id.addBookGen);
        eTxtPages = findViewById(R.id.addBookPages);
        eTxtImageUrl = findViewById(R.id.addBookUrlImage);
        eTxtDescription = findViewById(R.id.addBookDescription);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllBooksActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}