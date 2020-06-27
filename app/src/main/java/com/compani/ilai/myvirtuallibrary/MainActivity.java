package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnMyProfile, btnViewAllBooks, btnCurrentlyReadingBooks, btnWantToReadBooks, btnAllReadBooks,
    btnFavoriteBooks, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initButtons() {

        btnMyProfile = findViewById(R.id.btnMyProfile);
        btnViewAllBooks = findViewById(R.id.btnViewAllBooks);
        btnCurrentlyReadingBooks = findViewById(R.id.btnCurrentlyRead);
        btnWantToReadBooks = findViewById(R.id.btnWantToRead);
        btnAllReadBooks = findViewById(R.id.btnAllReadBooks);
        btnFavoriteBooks = findViewById(R.id.btnFavorite);
        btnAbout = findViewById(R.id.btnAbout);
    }
}