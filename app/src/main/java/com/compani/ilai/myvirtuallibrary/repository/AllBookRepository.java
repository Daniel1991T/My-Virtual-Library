package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.Nullable;

public class AllBookRepository extends CRUDRepository {

    public AllBookRepository(@Nullable Context context) {
        super(new DatabaseHelper(context), "ALL_BOOKS_TABLE");
    }

}
