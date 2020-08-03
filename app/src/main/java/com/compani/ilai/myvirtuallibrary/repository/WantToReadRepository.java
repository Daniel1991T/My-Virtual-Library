package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.compani.ilai.myvirtuallibrary.services.Book;

public class WantToReadRepository extends CRUDRepository<Integer> {

    public WantToReadRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "WANT_TO_READ_BOOKS_TABLE");
    }
}
