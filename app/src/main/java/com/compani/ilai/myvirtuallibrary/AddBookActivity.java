package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity {

    private Button btnAddBook;
    private EditText eTxtName, eTxtAuthor, eTxtPages, eTxtGen, eTxtImageUrl, eTxtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initView();
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBook();
                Intent intent = new Intent(AddBookActivity.this, AllBooksActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setBook() {
        String name = eTxtName.getText().toString().trim();
        String author = eTxtAuthor.getText().toString().trim();
        int pages = Integer.parseInt(eTxtPages.getText().toString().trim());
        String gen = eTxtGen.getText().toString().trim();
        String urlImage = eTxtImageUrl.getText().toString().trim();
        String description = eTxtDescription.getText().toString().trim();
        Book book = new Book(-1, name, author, pages, gen, urlImage, description);

        DatabaseHelper databaseHelper = new DatabaseHelper(AddBookActivity.this);
        boolean success = databaseHelper.addBookToAllBooksList(book);
        Toast.makeText(this, "Success = " + success, Toast.LENGTH_SHORT).show();
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
}