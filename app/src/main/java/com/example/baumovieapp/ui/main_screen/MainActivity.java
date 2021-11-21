package com.example.baumovieapp.ui.main_screen;

import static com.example.baumovieapp.util.Constants.ARG_MOVIE_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.baumovieapp.R;
import com.example.baumovieapp.adapter.MovieAdapter;
import com.example.baumovieapp.data.main_list.MovieModel;
import com.example.baumovieapp.databinding.ActivityMainBinding;
import com.example.baumovieapp.ui.movie_detail.DetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mViewModel;
    private MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String message = "Welcome to Movie App";
        Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        this.initComponents();
        this.initClicks();
        this.initObservers();


        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observeMovieData(binding.editText.getText().toString());
            }
        });
    }

    private void initComponents(){
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMovies.setItemAnimator(new DefaultItemAnimator());
        movieAdapter = new MovieAdapter(this);
        binding.rvMovies.setAdapter(movieAdapter);
    }

    private void initClicks() {
        movieAdapter.setOnClickListener((pos, movieModel) -> {
            Log.d("INIT_CLICK", "onClick: ");
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(ARG_MOVIE_ID, movieModel.getId());
            startActivity(intent);
        });
    }

    private void initObservers() {
        mViewModel.getMovieList().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.d("OBS", "onChanged: ");
                prepareRecycler(movieModels);
            }
        });


    }
    private void observeMovieData(String movie) {
        mViewModel.getMoviesData(movie);
    }
    private void prepareRecycler(List<MovieModel> models) {
        movieAdapter.setAdapterList(models);
    }

}