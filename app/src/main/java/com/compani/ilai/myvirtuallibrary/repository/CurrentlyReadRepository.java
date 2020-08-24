package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

public class CurrentlyReadRepository extends CRUDRepository {

    public CurrentlyReadRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "CURRENTLY_READING_BOOK_TABLE");
    }
}
