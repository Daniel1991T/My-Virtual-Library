package com.compani.ilai.myvirtuallibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.compani.ilai.myvirtuallibrary.Utils.LIBRARY_NAME_SP;


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
//    public static final String DATABASE_NAME = RegisterActivity.prefs.getString(LIBRARY_NAME_SP, null) + ".db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "book.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAllBooks = "CREATE TABLE IF NOT EXISTS " + ALL_BOOKS_TABLE + COLUMN_ALL_TABLES;
        db.execSQL(createTableAllBooks);

        String createTableCurrentlyReadingBook = "CREATE TABLE IF NOT EXISTS " + CURRENTLY_READING_BOOK_TABLE + " ("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY)";
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

        iniData();
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

        cv.put(COLUMN_BOOK_ID, book.getId());

        long insert = db.insert(CURRENTLY_READING_BOOK_TABLE, null, cv);
        return insert != -1;
    }

    public Book getBookById(int bookId) {
        String querySearch = "SELECT * FROM " + ALL_BOOKS_TABLE + " WHERE " + COLUMN_BOOK_ID + " = " + bookId;
        SQLiteDatabase db = this.getReadableDatabase();
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
            Log.d(TAG, "getAllBooksList: " + id + " " + name);
            book = new Book(id, name, author, pages, gen, urlImage, description);
            return book;
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
        long insert = db.insert(FAVORITE_BOOK_TABLE, null, cv);
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

    public int getAllBooksNumber() {
        String query = "SELECT COUNT(*) AS total FROM " + ALL_BOOKS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public int getAlreadyReadBooksNumber() {
        String query = "SELECT COUNT(*) AS total FROM " + ALREADY_READ_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public int getFavoriteBooksNumber() {
        String query = "SELECT COUNT(*) AS total FROM " + FAVORITE_BOOK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public Set<String> getGenresList() {
        Set<String> genresList = new HashSet<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_BOOK_GEN + " FROM " + ALL_BOOKS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String gen = cursor.getString(0);
                genresList.add(gen);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return genresList;
    }

    private void iniData() {
        ArrayList<Book> allBooks = new ArrayList<>();
        allBooks.add(new Book(1, "Deep Work", "Cal Newport", 304,
                "Dezvoltare personala", "https://cdn4.libris.ro/img/pozeprod/59/1000/32/1258010-JVYC.jpg",
                "IPOTEZA MUNCII PROFUNDE: abilitatea de a indeplini o munca profunda devine din ce in ce mai rara " +
                        "si, implicit, din ce in ce mai valoroasa in economia noastra. " +
                        "Drept urmare, cei foarte putini care cultiva si, mai apoi, transforma aceasta abilitate in esenta vietii lor profesionale, " +
                        "vor prospera. Deep Work are doua teluri: primul este de a convinge cititorii ca ipoteza muncii profunde este adevarata. " +
                        "Cel de‑al doilea, este de a-i invata pe cititori sa profite de aceasta realitate, antrenandu‑si mintea si transformandu‑si" +
                        " obiceiurile de lucru prin plasarea muncii profunde in centrul vietii lor profesionale."));
        allBooks.add(new Book(2, "Cum Vad eu lumea", "Albert Einstein", 312,
                "Stiinta si filosofie",
                "https://cdn4.libris.ro/img/pozeprod//59/1010/80/884804-TNIO.jpg",
                "Devenita simbol al stiintei, mai mult, simbol al unei intelepciuni care depaseste frontierele stiintei, " +
                        "ajungand la intrebarile esentiale despre ordinea universului si rostul nostru pe pamant, " +
                        "figura lui Einstein continua sa exercite o fascinatie fara egal, atat asupra oamenilor de stiinta, " +
                        "cat si asupra profanilor, cuceriti de clarviziunea, nonconformismul si umorul sau. " +
                        "Cum vad eu lumea reuneste articole si conferinte ale lui Einstein, " +
                        "definitorii pentru calea urmata in cercetarile sale stiintifice, pentru personalitatea si " +
                        "viziunea lui asupra lumii, texte prin care savantul a urmarit sa se apropie de publicul larg, " +
                        "fara a lasa in umbra rigoarea fizicianului."));
        allBooks.add(new Book(3, "Arta de a citi gandurile", "Henrik fexeus", 264,
                "Psihologie", "https://cdn4.libris.ro/img/pozeprod/59/1000/B6/833145-JVBN.jpg",
                "Citirea gandurilor nu este un mit. Este o realitate. " +
                        "Doar ca este putin diferita de ceea ce isi imagineaza majoritatea oamenilor. " +
                        "Cu instrumentele pe care le ofera aceasta carte, vei afla imediat ce gandeste si " +
                        "simte cealalta persoana si vei putea sa-i influentezi gandurile si sentimentele. " +
                        "Cand vei observa expresiile afisate pe fata sa, ii vei descifra emotiile. " +
                        "Vei putea detecta imediat orice lipsa de onestitate sau minciuna. Vei invata " +
                        "sa fii atent la modul in care ceilalti isi folosesc limbajul corpului " +
                        "si vocea atunci cand comunica. Folosind acelasi limbaj al corpului si " +
                        "acelasi ton al vocii ca interlocutorul tau, vei obtine intotdeauna o comunicare limpede " +
                        "si o intelegere precisa a lucrurilor pe care doresti sa le impartasesti: la o intalnire romantica, " +
                        "la un interviu pentru angajare sau in familie."
        ));
        allBooks.add(new Book(4, "Bun venit in univers", "Neil Degrasse", 480,
                "Astronomie", "https://cdn.dc5.ro/img-prod/2326883-0.jpeg",
                "Intr-un stil incitant, autorii povestesc cele mai recente descoperiri in astrofizica," +
                        " purtandu-si cititorii de la sistemul nostru solar pana la cele mai indepartate regiuni ale Universului cunoscut: " +
                        "Cum traiesc si cum mor stelele? De ce nu mai e Pluto planeta? Ce sanse sunt sa existe viata pe alte planete? " +
                        "Cum a luat nastere Universul? Suntem singuri sau facem parte dintr-un multivers?"));
        allBooks.add(new Book(4, "Origini", "Dan Brown", 600, "Literatura universala",
                "https://cdn4.libris.ro/img/pozeprod/59/1010/33/1192949-IMSD.jpg",
                "De aceasta data, profesorul Robert Langdon este invitat la Muzeul Guggenheim din Bilbao pentru a lua parte la ceremonia de dezvaluire a unei inventii care „va schimba fata stiintei pentru totdeauna“.\n" +
                        "Gazda este fostul student al profesorului, milionarul Edmond Kirsch, ale carui realizari high-tech l-au facut celebru in toata lumea. El va prezenta pentru prima data descoperirea care raspunde la doua dintre intrebarile fundamentale ale omenirii.\n" +
                        "Seara meticulos pregatita se transforma insa in haos, pretioasa descoperire a lui Kirsch risca sa fie pierduta pentru totdeauna, iar Langdon, insotit de eleganta directoare a muzeului, Ambra Vidal, este nevoit sa zboare la Barcelona pentru a localiza parola de decriptare a descoperirii lui Kirsch.\n" +
                        "Strabatand coridoarele intunecate ale istoriei secrete si extremismului religios, cei doi trebuie sa faca fata unui inamic periculos, al carui singur scop este sa il reduca la tacere pe Edmond Kirsch.\n" +
                        "Intr-o cursa contracronometru marcata de simboluri enigmatice si indicii oferite de arta moderna, Langdon si Vidal identifica, in cele din urma socanta descoperire a lui Kirsch si adevarul extraordinar pe care ea il contine."));

        for (Book book : allBooks) {
            addBookToAllBooksList(book);
        }
    }



















}
