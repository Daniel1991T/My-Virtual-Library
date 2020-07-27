package com.compani.ilai.myvirtuallibrary.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.compani.ilai.myvirtuallibrary.services.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.ALL_BOOKS_TABLE;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_AUTHOR;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_DESCRIPTION;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_GEN;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_ID;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_IMAGE_URL;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_NAME;
import static com.compani.ilai.myvirtuallibrary.repository.DatabaseHelper.COLUMN_BOOK_PAGES;


public abstract class CRUDRepository<T, ID> {

    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final String table;

    public CRUDRepository(SQLiteOpenHelper sqLiteOpenHelper, String table) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
        this.table = table;
    }

    public boolean remove(ID id) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + table + " WHERE " + COLUMN_BOOK_ID
                + " = " + id;

        Cursor cursor = db.rawQuery(queryToDelete, null);

        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }

    public ArrayList<Book> getBooksList () {
        ArrayList<Book> books = new ArrayList<>();

        String queryAllBooks;
        if (table.equals(ALL_BOOKS_TABLE)) {
            queryAllBooks = "SELECT * FROM " + table;
        } else {
            queryAllBooks = "SELECT * FROM " + ALL_BOOKS_TABLE + " INNER JOIN " + table + " ON " +
                    table + "." + COLUMN_BOOK_ID + " = " + ALL_BOOKS_TABLE + "." + COLUMN_BOOK_ID;
        }
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
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
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return books;
    }

    public boolean addBook(Book book) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (table.equals(ALL_BOOKS_TABLE)) {
            cv.put(COLUMN_BOOK_NAME, book.getName());
            cv.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
            cv.put(COLUMN_BOOK_PAGES, book.getPages());
            cv.put(COLUMN_BOOK_GEN, book.getGen());
            cv.put(COLUMN_BOOK_IMAGE_URL, book.getUrlImage());
            cv.put(COLUMN_BOOK_DESCRIPTION, book.getShortDescription());
        } else {
            cv.put(COLUMN_BOOK_ID, book.getId());
        }
        long insert = db.insert(table, null, cv);
        return insert != -1;
    }

    public Book getBookById(int bookId) {
        String querySearch = "SELECT * FROM " + ALL_BOOKS_TABLE + " WHERE " + COLUMN_BOOK_ID + " = " + bookId;
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySearch, null);
        Book book;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            int pages = cursor.getInt(3);
            String gen = cursor.getString(4);
            String urlImage = cursor.getString(5);
            String description = cursor.getString(6);
            book = new Book(id, name, author, pages, gen, urlImage, description);
            return book;
        }
        cursor.close();
        db.close();
        return null;
    }

    public int getNumberOfBooks() {
        String query;
        if (table.equals(ALL_BOOKS_TABLE)) {
            query = "SELECT COUNT(*) AS total FROM " + ALL_BOOKS_TABLE;
        } else {
            query = "SELECT COUNT(*) AS total FROM " + table;
        }
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public Set<String> getGenresList() {
        Set<String> genresList = new HashSet<>();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT " + COLUMN_BOOK_GEN + " FROM " + ALL_BOOKS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String genres = cursor.getString(0);
                genresList.add(genres);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return genresList;
    }

}
