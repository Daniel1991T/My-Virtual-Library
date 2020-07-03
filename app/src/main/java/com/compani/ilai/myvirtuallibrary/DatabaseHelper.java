package com.compani.ilai.myvirtuallibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DB";

    public static final String ALL_BOOKS_TABLE = "ALL_BOOKS_TABLE";
    public static final String COLUMN_BOOK_ID = "COLUMN_BOOK_ID";
    public static final String COLUMN_BOOK_NAME = "COLUMN_BOOK_NAME";
    public static final String COLUMN_BOOK_AUTHOR = "COLUMN_BOOK_AUTHOR";
    public static final String COLUMN_BOOK_PAGES = "COLUMN_BOOK_PAGES";
    public static final String COLUMN_BOOK_GEN = "COLUMN_BOOK_GEN";
    public static final String COLUMN_BOOK_IMAGE_URL = "COLUMN_BOOK_IMAGE_URL";
    public static final String COLUMN_BOOK_DESCRIPTION = "COLUMN_BOOK_DESCRIPTION";
    public static final String COLUMN_ALL_TABLES = " (" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BOOK_NAME + " TEXT, " + COLUMN_BOOK_AUTHOR +
            " TEXT, " + COLUMN_BOOK_PAGES + " INT, " + COLUMN_BOOK_GEN + " TEXT, " + COLUMN_BOOK_IMAGE_URL + " TEXT, " + COLUMN_BOOK_DESCRIPTION + " TEXT )";
    public static final String CURRENTLY_READING_BOOK_TABLE = "CURRENTLY_READING_BOOK_TABLE";
    public static final String WANT_TO_READ_BOOKS_TABLE = "WANT_TO_READ_BOOKS_TABLE";
    private static final String FAVORITE_BOOK_TABLE = "FAVORITE_BOOK_TABLE";
    public static final String ALREADY_READ_TABLE = "ALREADY_READ_TABLE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "book.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAllBooks = "CREATE TABLE IF NOT EXISTS " + ALL_BOOKS_TABLE + COLUMN_ALL_TABLES;
        db.execSQL(createTableAllBooks);

        String createTableCurrentlyReadingBook = "CREATE TABLE IF NOT EXISTS " + CURRENTLY_READING_BOOK_TABLE + " (" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableCurrentlyReadingBook);

        String createTableWantToReadBooks = "CREATE TABLE IF NOT EXISTS " + WANT_TO_READ_BOOKS_TABLE + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableWantToReadBooks);

        String createTableFavoriteBooks = "CREATE TABLE IF NOT EXISTS " + FAVORITE_BOOK_TABLE + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableFavoriteBooks);

        String createTableAlreadyReadBooks = "CREATE TABLE IF NOT EXISTS " + ALREADY_READ_TABLE + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
        db.execSQL(createTableAlreadyReadBooks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ALL_BOOKS_TABLE);
        onCreate(db);
    }

    public boolean addBookToAllBooksList(Book book) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_NAME, book.getName());
        cv.put(COLUMN_BOOK_AUTHOR, book.getAuthor());
        cv.put(COLUMN_BOOK_PAGES, book.getPages());
        cv.put(COLUMN_BOOK_GEN, book.getGen());
        cv.put(COLUMN_BOOK_IMAGE_URL, book.getUrlImage());
        cv.put(COLUMN_BOOK_DESCRIPTION, book.getShortDescription());

        long insert = db.insert(ALL_BOOKS_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<Book> getAllBooksList() {
        ArrayList<Book> listOfAllBooks = new ArrayList<>();

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
        cursor.close();
        db.close();
        return listOfAllBooks;
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
        return insert != -1;
    }

    public Book getBookById(int bookId) {
        String querySearch = "SELECT * FROM " + ALL_BOOKS_TABLE + " WHERE " + COLUMN_BOOK_ID + " = " + bookId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querySearch, null);
        Book book;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                int pages = cursor.getInt(3);
                String gen = cursor.getString(4);
                String urlImage = cursor.getString(5);
                String description = cursor.getString(6);
                Log.d(TAG, "getAllBooksList: " + id + " " + name);
                book = new Book(id, name, author, pages, gen, urlImage, description);
                return book;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return null;
    }

    public boolean addToWantToReadBooks(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_ID, book.getId());

        long insert = db.insert(WANT_TO_READ_BOOKS_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<Book> getWantToReadBooks() {
        ArrayList<Book> wantToReadList = new ArrayList<>();
        String queryWantToRead = "SELECT * FROM " + ALL_BOOKS_TABLE + " INNER JOIN " + WANT_TO_READ_BOOKS_TABLE + " ON " +
                WANT_TO_READ_BOOKS_TABLE + "." + COLUMN_BOOK_ID + " = " + ALL_BOOKS_TABLE + "." + COLUMN_BOOK_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryWantToRead, null);

        if (cursor.moveToNext()) {
            do {
                int bookId = cursor.getInt(0);
                String bookName = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                int bookPages = cursor.getInt(3);
                String bookGen = cursor.getString(4);
                String bookUrlImage = cursor.getString(5);
                String bookDescription = cursor.getString(6);


                Book book = new Book(bookId, bookName, bookAuthor, bookPages, bookGen, bookUrlImage, bookDescription);
                wantToReadList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return wantToReadList;
    }

    public  ArrayList<Book> getCurrentlyReadingBooks() {
        ArrayList<Book> currentlyReadList = new ArrayList<>();
        String queryCurrentlyRead = "SELECT * FROM " + ALL_BOOKS_TABLE + " INNER JOIN " + CURRENTLY_READING_BOOK_TABLE + " ON " +
                CURRENTLY_READING_BOOK_TABLE + "." + COLUMN_BOOK_ID + " = " + ALL_BOOKS_TABLE + "." + COLUMN_BOOK_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryCurrentlyRead, null);

        if (cursor.moveToNext()) {
            do {
                int bookId = cursor.getInt(0);
                String bookName = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                int bookPages = cursor.getInt(3);
                String bookGen = cursor.getString(4);
                String bookUrlImage = cursor.getString(5);
                String bookDescription = cursor.getString(6);


                Book book = new Book(bookId, bookName, bookAuthor, bookPages, bookGen, bookUrlImage, bookDescription);
                currentlyReadList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return currentlyReadList;
    }

    public boolean addToFavoriteBooks(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_ID, book.getId());
        long insert = db.insert(ALREADY_READ_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<Book> getFavoriteBooks() {
        ArrayList<Book> favoriteBookList = new ArrayList<>();
        String queryFavorite = "SELECT * FROM " + ALL_BOOKS_TABLE + " INNER JOIN " + FAVORITE_BOOK_TABLE + " ON " +
                FAVORITE_BOOK_TABLE + "." + COLUMN_BOOK_ID + " = " + ALL_BOOKS_TABLE + "." + COLUMN_BOOK_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryFavorite, null);

        if (cursor.moveToNext()) {
            do {
                int bookId = cursor.getInt(0);
                String bookName = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                int bookPages = cursor.getInt(3);
                String bookGen = cursor.getString(4);
                String bookUrlImage = cursor.getString(5);
                String bookDescription = cursor.getString(6);


                Book book = new Book(bookId, bookName, bookAuthor, bookPages, bookGen, bookUrlImage, bookDescription);
                favoriteBookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteBookList;
    }

    public boolean addToAlreadyReadBooks(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_ID, book.getId());
        long insert = db.insert(ALREADY_READ_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        ArrayList<Book> alreadyReadList = new ArrayList<>();
        String queryAlreadyRead = "SELECT * FROM " + ALL_BOOKS_TABLE + " INNER JOIN " + ALREADY_READ_TABLE + " ON " +
                ALREADY_READ_TABLE + "." + COLUMN_BOOK_ID + " = " + ALL_BOOKS_TABLE + "." + COLUMN_BOOK_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryAlreadyRead, null);

        if (cursor.moveToNext()) {
            do {
                int bookId = cursor.getInt(0);
                String bookName = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                int bookPages = cursor.getInt(3);
                String bookGen = cursor.getString(4);
                String bookUrlImage = cursor.getString(5);
                String bookDescription = cursor.getString(6);


                Book book = new Book(bookId, bookName, bookAuthor, bookPages, bookGen, bookUrlImage, bookDescription);
                alreadyReadList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alreadyReadList;
    }

    public boolean deleteFromAllBooksList(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + ALL_BOOKS_TABLE + " WHERE " +
                COLUMN_BOOK_ID + " = " + book.getId();

        Cursor cursor = db.rawQuery(queryToDelete, null);

        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }

    public boolean deleteFromAlreadyReadBooksList(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + ALREADY_READ_TABLE + " WHERE " +
                COLUMN_BOOK_ID + " = " + book.getId();

        Cursor cursor = db.rawQuery(queryToDelete, null);
        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }

    public boolean deleteFromWantToReadBookList(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + WANT_TO_READ_BOOKS_TABLE + " WHERE " +
                COLUMN_BOOK_ID + " = " + book.getId();
        Cursor cursor = db.rawQuery(queryToDelete, null);
        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }

    public boolean deleteFormCurrentlyReadingBookList(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + CURRENTLY_READING_BOOK_TABLE + " WHERE " +
                COLUMN_BOOK_ID + " = " + book.getId();
        Cursor cursor = db.rawQuery(queryToDelete, null);
        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }

    public boolean deleteFormFavoriteBookList(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryToDelete = "DELETE FROM " + FAVORITE_BOOK_TABLE + " WHERE " +
                COLUMN_BOOK_ID + " = " + book.getId();
        Cursor cursor = db.rawQuery(queryToDelete, null);
        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }

















}
