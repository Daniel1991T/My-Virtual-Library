package com.compani.ilai.myvirtuallibrary.view.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.compani.ilai.myvirtuallibrary.R;
import com.compani.ilai.myvirtuallibrary.repository.AllBookRepository;
import com.compani.ilai.myvirtuallibrary.repository.AlreadyReadRepository;
import com.compani.ilai.myvirtuallibrary.repository.FavoriteRepository;
import com.compani.ilai.myvirtuallibrary.services.GenresRecViewAdapter;
import com.compani.ilai.myvirtuallibrary.view.loginRegister.RegisterActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import static com.compani.ilai.myvirtuallibrary.Utils.LIBRARY_NAME_SP;
import static com.compani.ilai.myvirtuallibrary.Utils.USERNAME_SP;

public class MyProfileActivity extends AppCompatActivity {

    TextView messageEditText;
    String username, libraryName, allNumberBooks, numberOfReadingBooks, numberOfFavoriteBooks;
    AllBookRepository allBookRepository = new AllBookRepository(this);
    AlreadyReadRepository alreadyReadRepository = new AlreadyReadRepository(this);
    FavoriteRepository favoriteRepository = new FavoriteRepository(this);
    RecyclerView mRecyclerView;
    GenresRecViewAdapter mGenresRecViewAdapter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initView();
        messageEditText.setText(getString(R.string.my_profile_hello) + " " + username + " " +
                getString(R.string.my_profile_message1) + " " +libraryName + " " +
                getString(R.string.my_profile_message2) + " " +allNumberBooks + " " +
                getString(R.string.my_profile_message3) + " " + numberOfReadingBooks + " " +
                getString(R.string.my_profile_message4) + " " + numberOfFavoriteBooks + " " +
                getString(R.string.my_profile_message5));

        mRecyclerView.setAdapter(mGenresRecViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> genres = new ArrayList<>(allBookRepository.getGenresList());
        Collections.sort(genres);
        mGenresRecViewAdapter.setGenres(genres);

    }


    private void initView() {

        messageEditText = findViewById(R.id.my_profile_txtView);
//
//        username = RegisterActivity.prefs.getString(USERNAME_SP, null);
//        libraryName = RegisterActivity.prefs.getString(LIBRARY_NAME_SP, null);
        username = "URSENAME";
        libraryName = "LIBRARYNAME";
        allNumberBooks = String.valueOf(allBookRepository.getNumberOfBooks());
        numberOfReadingBooks = String.valueOf(alreadyReadRepository.getNumberOfBooks());
        numberOfFavoriteBooks = String.valueOf(favoriteRepository.getNumberOfBooks());
        mRecyclerView = findViewById(R.id.myProfile_recyclerView);
        mGenresRecViewAdapter = new GenresRecViewAdapter(this);
    }
}