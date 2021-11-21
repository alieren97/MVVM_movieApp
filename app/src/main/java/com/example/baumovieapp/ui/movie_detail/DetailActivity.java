package com.example.baumovieapp.ui.movie_detail;

import static com.example.baumovieapp.util.Constants.ARG_MOVIE_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.baumovieapp.R;
import com.example.baumovieapp.data.main_list.MovieModel;

import com.example.baumovieapp.databinding.DetailActivityBinding;

public class DetailActivity extends AppCompatActivity {

    private DetailActivityBinding binding;
    private DetailViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        binding = DetailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        initObservers();
        getMovieDetail();
    }



    private void initObservers() {
        mViewModel.getMovieDetaill().observe(this, new Observer<MovieModel>() {
            @Override
            public void onChanged(MovieModel movieModel) {
                Log.d("INIT_OBSERVE", "onChanged: ");
                prepareView(movieModel);
            }
        });
    }
    private void prepareView(MovieModel model) {
        if (!TextUtils.isEmpty(model.getTitle()))
            binding.movieName.setText(model.getTitle());
        if (!TextUtils.isEmpty(model.getBackdropPath()))
            Glide.with(binding.ivMovie.getContext())
                    .load("https://image.tmdb.org/t/p/original/"+model.getBackdropPath())
                    .into(binding.ivMovie);
        if(!TextUtils.isEmpty(model.getOverview()))
            binding.movieOverview.setText(model.getOverview());
        if(!TextUtils.isEmpty(model.getReleaseDate()))
            binding.tvReleaseDate.setText(model.getReleaseDate());

    }


    private void getMovieDetail() {
        int movideId = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movideId = extras.getInt(ARG_MOVIE_ID, 0);
        }
        if (movideId != 0)
            mViewModel.getMovieDetail(movideId);
        else
            Toast.makeText(this, "Movie not found.", Toast.LENGTH_SHORT).show();

    }



}