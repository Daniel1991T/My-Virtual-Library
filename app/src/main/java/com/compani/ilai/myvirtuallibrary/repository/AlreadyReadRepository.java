package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.compani.ilai.myvirtuallibrary.services.Book;

public class AlreadyReadRepository extends CRUDRepository<Integer> {


    public AlreadyReadRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "ALREADY_READ_TABLE");
    }
}
