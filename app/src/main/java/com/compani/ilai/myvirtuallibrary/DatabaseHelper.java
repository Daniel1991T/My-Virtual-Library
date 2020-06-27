package com.compani.ilai.myvirtuallibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ALL_BOOKS_TABLE = "ALL_BOOKS_TABLE";
    public static final String COLUMN_BOOK_ID = "COLUMN_BOOK_ID";
    public static final String COLUMN_BOOK_NAME = "COLUMN_BOOK_NAME";
    public static final String COLUMN_BOOK_AUTHOR = "COLUMN_BOOK_AUTHOR";
    public static final String COLUMN_BOOK_PAGES = "COLUMN_BOOK_PAGES";
    public static final String COLUMN_BOOK_GEN = "COLUMN_BOOK_GEN";
    public static final String COLUMN_BOOK_IMAGE_URL = "COLUMN_BOOK_IMAGE_URL";
    public static final String COLUMN_BOOK_DESCRIPTION = "COLUMN_BOOK_DESCRIPTION";
    public static final String COLUMN_ALL_TABLES = " (" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BOOK_NAME + " TEXT, " + COLUMN_BOOK_AUTHOR +
            " TEXT, " + COLUMN_BOOK_PAGES + " INT, " + COLUMN_BOOK_GEN + " TEXT, " + COLUMN_BOOK_IMAGE_URL + " TEXT, " + COLUMN_BOOK_DESCRIPTION + "TEXT )";
    public static final String CURRENTLY_READING_BOOK_TABLE = "CURRENTLY_READING_BOOK_TABLE";

    public DatabaseHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAllBooks = "CREATE TABLE " + ALL_BOOKS_TABLE + COLUMN_ALL_TABLES;
        db.execSQL(createTableAllBooks);

        String createTableCurrentlyReadingBook = "CREATE TABLE " + CURRENTLY_READING_BOOK_TABLE + COLUMN_ALL_TABLES;
        db.execSQL(createTableCurrentlyReadingBook);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addBookToAllBooksList(Book book) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        cv.put(COLUMN_BOOK_PAGES, book.getPages());
        cv.put(COLUMN_BOOK_GEN, book.getGen());
        cv.put(COLUMN_BOOK_IMAGE_URL, book.getUrlImage());
        cv.put(COLUMN_BOOK_DESCRIPTION, book.getShortDescription());

        long insert = db.insert(ALL_BOOKS_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public List<Book> getAllBooksList() {
        List<Book> listOfAllBooks = new ArrayList<>();

        String queryAllBooks = "SELECT * FROM " + ALL_BOOKS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryAllBooks, null);

        if (cursor.moveToFirst()) {
            do {
                int bookId = cursor.getInt(0);
                String bookName = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                int bookPages = cursor.getInt(3);
                String bookGen = cursor.getString(4);
                String bookUrlImage = cursor.getString(5);
                String bookDescription = cursor.getString(6);

                Book book = new Book(bookId, bookName, bookAuthor, bookPages, bookGen, bookUrlImage, bookDescription);
                listOfAllBooks.add(book);
            } while (cursor.moveToNext());
        }
        return listOfAllBooks;
    }

    public boolean deleteOfAllBooksList(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + ALL_BOOKS_TABLE + " WHERE " +
                COLUMN_BOOK_ID + " = " + book.getId();

        Cursor cursor = db.rawQuery(queryToDelete, null);

        return cursor.moveToFirst();
    }

    public boolean addBookToCurrentlyReadBookList(Book book) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        cv.put(COLUMN_BOOK_PAGES, book.getPages());
        cv.put(COLUMN_BOOK_GEN, book.getGen());
        cv.put(COLUMN_BOOK_IMAGE_URL, book.getUrlImage());
        cv.put(COLUMN_BOOK_DESCRIPTION, book.getShortDescription());

        long insert = db.insert(CURRENTLY_READING_BOOK_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

















}
