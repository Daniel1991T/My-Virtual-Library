package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import static com.compani.ilai.myvirtuallibrary.Utils.LIBRARY_NAME_SP;
import static com.compani.ilai.myvirtuallibrary.Utils.PROFILE;
import static com.compani.ilai.myvirtuallibrary.Utils.USERNAME_SP;

public class MyProfileActivity extends AppCompatActivity {

    TextView editUsername, editLibraryName, editAllNumberBooks, editNumberReadingBooks, editNumberFavoriteBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initView();

//        editUsername.setText(RegisterActivity.prefs.getString(USERNAME_SP, null));
//        editLibraryName.setText(RegisterActivity.prefs.getString(LIBRARY_NAME_SP, null));
    }


    private void initView() {
        editUsername = findViewById(R.id.mpEditUsername);
        editLibraryName = findViewById(R.id.mpEditLibraryName);
        editAllNumberBooks = findViewById(R.id.mpEditNumberBooks);
        editNumberReadingBooks = findViewById(R.id.mpEditNumberReads);
        editNumberFavoriteBooks = findViewById(R.id.mpEditNumberFavoriteBooks);

    }
}