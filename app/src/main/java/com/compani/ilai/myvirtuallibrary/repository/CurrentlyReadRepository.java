package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.compani.ilai.myvirtuallibrary.services.Book;

public class CurrentlyReadRepository extends CRUDRepository<Book, Integer> {

    public CurrentlyReadRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "CURRENTLY_READING_BOOK_TABLE");
    }
}
