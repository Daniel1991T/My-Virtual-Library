package com.compani.ilai.myvirtuallibrary.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.compani.ilai.myvirtuallibrary.repository.AlreadyReadRepository;
import com.compani.ilai.myvirtuallibrary.repository.CurrentlyReadRepository;
import com.compani.ilai.myvirtuallibrary.repository.FavoriteRepository;
import com.compani.ilai.myvirtuallibrary.repository.WantToReadRepository;
import com.compani.ilai.myvirtuallibrary.view.book.BooksActivity;
import com.compani.ilai.myvirtuallibrary.R;
import com.compani.ilai.myvirtuallibrary.repository.AllBookRepository;
import com.compani.ilai.myvirtuallibrary.repository.CRUDRepository;

import java.util.ArrayList;
import java.util.List;

import static com.compani.ilai.myvirtuallibrary.view.book.AllBooksActivity.PARENT_ALL_BOOKS;
import static com.compani.ilai.myvirtuallibrary.view.book.AlreadyReadActivity.PARENT_ALREADY_READ;
import static com.compani.ilai.myvirtuallibrary.view.book.CurrentlyReadActivity.PARENT_CURRENTLY_READING;
import static com.compani.ilai.myvirtuallibrary.Utils.BOOK_ID;
import static com.compani.ilai.myvirtuallibrary.view.book.FavoriteActivity.PARENT_FAVORITE_BOOKS;
import static com.compani.ilai.myvirtuallibrary.view.book.WantToReadActivity.PARENT_WANT_TO_READ;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> implements Filterable {

    public static final String TAG = "BookRecViewAdapter";

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Book> filterBook;
    private Context mContext;
    private String parentActivity;

    public BookRecViewAdapter(Context context, String parentActivity) {
        mContext = context;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtName.setText(books.get(position).getName());
        Glide.with(mContext).asBitmap().load(books.get(position).getUrlImage()).into(holder.imgBook);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BooksActivity.class);
                intent.putExtra(BOOK_ID, books.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtGen.setText(books.get(position).getGen());

        if (books.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            switch (parentActivity) {
                case PARENT_ALL_BOOKS:
                    removeBook(holder, position, new AllBookRepository(mContext));
                    break;
                case PARENT_ALREADY_READ:
                    removeBook(holder, position, new AlreadyReadRepository(mContext));
                    break;
                case PARENT_WANT_TO_READ:
                    removeBook(holder, position, new WantToReadRepository(mContext));
                    break;
                case PARENT_CURRENTLY_READING:
                    removeBook(holder, position, new CurrentlyReadRepository(mContext));
                    break;
                case PARENT_FAVORITE_BOOKS:
                    removeBook(holder, position, new FavoriteRepository(mContext));
                    break;
                default:
                    break;
            }
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    private void removeBook(@NonNull ViewHolder holder, final int position, final CRUDRepository crudRepository) {
        holder.txtBookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want delete " +
                        books.get(position).getName() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean removedSuccessfully = crudRepository.remove(books.get(position).getId());
                        if (removedSuccessfully) {
                            Toast.makeText(mContext, "Book Removed!", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        filterBook = new ArrayList<>(books);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return bookFilter;
    }

    private Filter bookFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Book> filterBooksList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 || constraint.equals("All Genres")) {
                filterBooksList.addAll(filterBook);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Book item : filterBook) {
                    if (item.getGen().toLowerCase().contains(filterPattern)) {
                        filterBooksList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterBooksList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            books.clear();
            books.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private ImageView imgBook;
        private TextView txtName;
        private ImageView downArrow, upArrow;
        private RelativeLayout expandedRelLayout;
        private TextView txtAuthor, txtGen, txtBookDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBookItem);
            txtName = itemView.findViewById(R.id.itemTxtBookName);
            txtAuthor = itemView.findViewById(R.id.itemEditAuthorTxt);
            txtGen = itemView.findViewById(R.id.itemEditGenTxt);

            downArrow = itemView.findViewById(R.id.itemBtnDownArrow);
            upArrow = itemView.findViewById(R.id.itemBtnUpArrow);

            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            txtBookDelete = itemView.findViewById(R.id.txtBookDelete);
        }
    }
}
