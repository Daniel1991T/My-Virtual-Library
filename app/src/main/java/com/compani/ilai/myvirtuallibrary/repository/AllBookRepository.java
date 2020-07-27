package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.Nullable;

import com.compani.ilai.myvirtuallibrary.services.Book;

public class AllBookRepository extends CRUDRepository<Book, Integer> {

    public AllBookRepository(@Nullable Context context) {
        super(new DatabaseHelper(context), "ALL_BOOKS_TABLE");
    }

}
