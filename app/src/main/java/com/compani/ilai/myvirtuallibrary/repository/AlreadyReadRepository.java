package com.compani.ilai.myvirtuallibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

public class AlreadyReadRepository extends CRUDRepository {


    public AlreadyReadRepository(@NonNull Context context) {
        super(new DatabaseHelper(context), "ALREADY_READ_TABLE");
    }
}
