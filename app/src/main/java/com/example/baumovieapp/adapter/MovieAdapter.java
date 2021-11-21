package com.example.baumovieapp.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baumovieapp.R;
import com.example.baumovieapp.data.main_list.MovieModel;
import com.example.baumovieapp.databinding.ItemMovieBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolders> {
    private List<MovieModel> movies;
    private Context context;
    private ItemClickListener itemClickListener;

    public MovieAdapter(Context context){
        this.context = context;
        this.movies= new ArrayList<>();
    }

    public void setAdapterList(List<MovieModel> movies){
        this.movies.clear();
        this.movies.addAll(movies);
        this.notifyDataSetChanged();
    }
    @NotNull
    @Override
    public MovieViewHolders onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolders(binding);
    }
    @Override
    public void onBindViewHolder(MovieViewHolders holder, int position) {
        MovieModel movieModel = movies.get(position);
        if (!TextUtils.isEmpty(movieModel.getPosterPath()))
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original/" + movieModel.getPosterPath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.binding.ivThumbnail);
        if (!TextUtils.isEmpty(movieModel.getTitle()))
            holder.binding.movieTitle.setText(movieModel.getTitle());
        if (!TextUtils.isEmpty(movieModel.getPopularity().toString()))
            holder.binding.moviePopularity.setText("Popularity : " + movieModel.getPopularity().toString());

    }

    private MovieModel getItem(int pos) {
        return movies.get(pos);
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }


    public void setOnClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(int pos, MovieModel movieModel);
    }
    public class MovieViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemMovieBinding binding;
        public MovieViewHolders(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), getItem(getAdapterPosition()));
        }
    }
}
