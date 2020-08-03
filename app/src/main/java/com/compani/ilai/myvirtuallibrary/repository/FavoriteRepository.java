package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.compani.ilai.myvirtuallibrary.services.Book;

public class FavoriteRepository extends CRUDRepository<Integer> {

    public FavoriteRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "FAVORITE_BOOK_TABLE");
    }

}
