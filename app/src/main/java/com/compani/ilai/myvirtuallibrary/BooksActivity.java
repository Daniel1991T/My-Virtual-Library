package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BooksActivity extends AppCompatActivity {

    private TextView txtBookName, txtAuthor, txtPages, txtGen, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        initView();
    }

    private void initView() {
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAutorName);
        txtPages = findViewById(R.id.txtPages);
        txtGen = findViewById(R.id.txtEditGen);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToWantToRead = findViewById(R.id.btnAddToWantToReadLIst);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyReadList);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);

        bookImage = findViewById(R.id.imgBook);
    }
}