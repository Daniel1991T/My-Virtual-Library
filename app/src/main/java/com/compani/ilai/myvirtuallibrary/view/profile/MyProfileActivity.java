package com.compani.ilai.myvirtuallibrary.view.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.compani.ilai.myvirtuallibrary.R;
import com.compani.ilai.myvirtuallibrary.repository.AllBookRepository;
import com.compani.ilai.myvirtuallibrary.repository.AlreadyReadRepository;
import com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper;
import com.compani.ilai.myvirtuallibrary.repository.FavoriteRepository;

public class MyProfileActivity extends AppCompatActivity {

    TextView editUsername, editLibraryName, editAllNumberBooks, editNumberReadingBooks, editNumberFavoriteBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initView();
        AllBookRepository allBookRepository = new AllBookRepository(this);
        AlreadyReadRepository alreadyReadRepository = new AlreadyReadRepository(this);
        FavoriteRepository favoriteRepository = new FavoriteRepository(this);

//        editUsername.setText(RegisterActivity.prefs.getString(USERNAME_SP, null));
//        editLibraryName.setText(RegisterActivity.prefs.getString(LIBRARY_NAME_SP, null));
        editAllNumberBooks.setText(String.valueOf(allBookRepository.getNumberOfBooks()));
        editNumberReadingBooks.setText(String.valueOf(alreadyReadRepository.getNumberOfBooks()));
        editNumberFavoriteBooks.setText(String.valueOf(favoriteRepository.getNumberOfBooks()));
    }


    private void initView() {
        editUsername = findViewById(R.id.mpEditUsername);
        editLibraryName = findViewById(R.id.mpEditLibraryName);
        editAllNumberBooks = findViewById(R.id.mpEditNumberBooks);
        editNumberReadingBooks = findViewById(R.id.mpEditNumberReads);
        editNumberFavoriteBooks = findViewById(R.id.mpEditNumberFavoriteBooks);

    }
}