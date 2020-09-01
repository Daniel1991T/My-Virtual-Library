package com.compani.ilai.myvirtuallibrary.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compani.ilai.myvirtuallibrary.R;

import java.util.ArrayList;
import java.util.List;

public class GenresRecViewAdapter extends RecyclerView.Adapter<GenresRecViewAdapter.GenresViewHolder> {

    List<String> genres = new ArrayList<>();
    Context context;

    public GenresRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genres_item, parent, false);
        return new GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {

        holder.genresTxtView.setText(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    static class GenresViewHolder extends RecyclerView.ViewHolder{

        TextView genresTxtView;

        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            genresTxtView = itemView.findViewById(R.id.my_profile_txtView_genres);
        }
    }
}
