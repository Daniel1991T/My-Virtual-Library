package com.compani.ilai.myvirtuallibrary;

import android.content.Context;

import com.compani.ilai.myvirtuallibrary.services.Book;

import java.util.ArrayList;

public class Utils {

    public static final String PROFILE = "Profile";
    public static final String USERNAME_SP = "usernameKey";
    public static final String LIBRARY_NAME_SP = "libraryNameKey";

    public static final String BOOK_ID = "book_id";

    private static Utils instance;
    private static Context mContext;
    private static ArrayList<Book> allBooks;

    private Utils(Context context) {
        if (allBooks == null) {
            allBooks = new ArrayList<>();
        }
        mContext = context;
    }

    public static Utils getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new Utils(mContext);
        }
    }

}
