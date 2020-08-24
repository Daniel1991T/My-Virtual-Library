package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;


public class FavoriteRepository extends CRUDRepository {

    public FavoriteRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "FAVORITE_BOOK_TABLE");
    }

}
