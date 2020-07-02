package com.compani.ilai.myvirtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.compani.ilai.myvirtuallibrary.Utils.BOOK_ID;

public class BooksActivity extends AppCompatActivity {

    public static final String TAG = "BookActivity";

    private TextView txtBookName, txtAuthor, txtPages, txtGen, txtDescription;
    private Button btnAddToWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        initView();

        Intent intent = getIntent();
        if (intent != null) {
            int bookId = intent.getIntExtra(BOOK_ID, -1);
            Log.d(TAG, "onCreate: " + bookId);
            if (bookId != -1) {
                databaseHelper = new DatabaseHelper(BooksActivity.this);
                Book incomingBook = databaseHelper.getBookById(bookId);
                if (incomingBook != null) {
                    setData(incomingBook);

                    handleWantToReadBook(incomingBook);
                    handleCurrentlyReadingBook(incomingBook);
                    handleAlreadyReadBook(incomingBook);
                    handleFavoriteBook(incomingBook);

                }
            }
        }
    }

    private void handleWantToReadBook(final Book book) {
        ArrayList<Book> wantToReadBooks = (ArrayList<Book>) databaseHelper.getWantToReadBooks();

        boolean existInWantToReadBooks = false;
        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInWantToReadBooks = true;
                break;
            }
        }
        if (existInWantToReadBooks) {
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (databaseHelper.addToWantToReadBooks(book)) {
                        Toast.makeText(BooksActivity.this, "Book Added To Wishlist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BooksActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BooksActivity.this, "Something wrong happened, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBook(final Book book) {
        ArrayList<Book> currentlyReadList = databaseHelper.getCurrentlyReadingBooks();
        boolean existInCurrentlyReadList = false;
        for (Book b : currentlyReadList) {
            if (b.getId() == book.getId()) {
                existInCurrentlyReadList = true;
                break;
            }
        }
        if (existInCurrentlyReadList) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
           btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (databaseHelper.addBookToCurrentlyReadBookList(book)) {
                       Toast.makeText(BooksActivity.this, "Book Added To Currently Reading!", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(BooksActivity.this, CurrentlyReadingActivity.class);
                       startActivity(intent);
                   } else {
                       Toast.makeText(BooksActivity.this, "Something wrong happened, try again!", Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }
    }

    private void handleAlreadyReadBook(final Book book) {
        ArrayList<Book> alreadyReadBooks = databaseHelper.getCurrentlyReadingBooks();
        boolean existInAlreadyReadBook = false;
        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existInAlreadyReadBook = true;
                break;
            }
        }
        if (existInAlreadyReadBook) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
           btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (databaseHelper.addToAlreadyReadBooks(book)) {
                       Toast.makeText(BooksActivity.this, "Book Added To Currently Reading!", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(BooksActivity.this, AlreadyReadActivity.class);
                       startActivity(intent);
                   } else {
                       Toast.makeText(BooksActivity.this, "Something wrong happened, try again!", Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }
    }

    private void handleFavoriteBook(final  Book book) {
        ArrayList<Book> favoriteBook = databaseHelper.getCurrentlyReadingBooks();
        boolean existInFavoriteBook = false;
        for (Book b : favoriteBook) {
            if (b.getId() == book.getId()) {
                existInFavoriteBook = true;
                break;
            }
        }
        if (existInFavoriteBook) {
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (databaseHelper.addToFavoriteBooks(book)) {
                        Toast.makeText(BooksActivity.this, "Book Added To Currently Reading!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BooksActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BooksActivity.this, "Something wrong happened, try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtGen.setText(book.getGen());
        txtDescription.setText(book.getShortDescription());
        Glide.with(this).asBitmap()
                .load(book.getUrlImage()).into(bookImage);
    }

    private void initView() {
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAutorName);
        txtPages = findViewById(R.id.txtPages);
        txtGen = findViewById(R.id.txtEditGen);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToWantToRead = findViewById(R.id.btnAddToWantToReadLIst);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyReadList);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);

        bookImage = findViewById(R.id.imgBook);

    }
}