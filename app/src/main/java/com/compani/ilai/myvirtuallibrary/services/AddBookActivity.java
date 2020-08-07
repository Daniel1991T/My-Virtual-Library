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
import com.google.android.material.textfield.TextInputLayout;

public class AddBookActivity extends AppCompatActivity {

    private Button btnAddBook;
    private EditText eTxtName, eTxtAuthor, eTxtPages, eTxtGen, eTxtImageUrl, eTxtDescription;
    private TextInputLayout iTxtLayoutName, iTxtLayoutAuthor, iTxtLayoutPage, iTxtLayoutGen,
            iTxtLayoutImageUrl, iTxtLayoutDescription;
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
        int pages;
        try {
            pages = Integer.parseInt(eTxtPages.getText().toString().trim());
        } catch (Exception e) {
            iTxtLayoutPage.setError("Enter number of pages!");
            pages = -1;
            e.getMessage();
        }
        String urlImage = eTxtImageUrl.getText().toString().trim();
        String description = eTxtDescription.getText().toString().trim();
        String gen = eTxtGen.getText().toString().trim();

        if (name.isEmpty()) {
            iTxtLayoutName.setError("Enter a book name!");
            return false;
        } else {
            iTxtLayoutName.setError(null);
        }
        if (author.isEmpty()){
            iTxtLayoutAuthor.setError("Enter name of author!");
            return false;
        } else {
            iTxtLayoutAuthor.setError(null);
        }
        if (urlImage.isEmpty()) {
            iTxtLayoutImageUrl.setError("Enter an url!");
            return false;
        } else {
            iTxtLayoutImageUrl.setError(null);
        }
        if (gen.isEmpty()) {
            iTxtLayoutGen.setError("Enter a book genres!");
            return false;
        } else {
            iTxtLayoutGen.setError(null);
        }
        if (pages < 0) {
            iTxtLayoutPage.setError("Enter number of pages!");
            return false;
        } else {
            iTxtLayoutPage.setError(null);
        }
        if (description.isEmpty()) {
            iTxtLayoutDescription.setError("Enter a descriptions");
            return false;
        } else {
            iTxtLayoutDescription.setError(null);
        }
        Book book = new Book(-1, name, author, pages, gen, urlImage, description);
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

        iTxtLayoutName = findViewById(R.id.iTxtLayoutAddName);
        iTxtLayoutAuthor = findViewById(R.id.iTxtLayoutAddAuthor);
        iTxtLayoutPage = findViewById(R.id.iTxtLayoutAddPages);
        iTxtLayoutGen = findViewById(R.id.iTxtLayoutAddGen);
        iTxtLayoutImageUrl = findViewById(R.id.iTxtLayoutAddUrl);
        iTxtLayoutDescription = findViewById(R.id.iTxtLayoutAddDescription);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllBooksActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}