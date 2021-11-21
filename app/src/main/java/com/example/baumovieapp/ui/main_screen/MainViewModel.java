package com.example.baumovieapp.ui.main_screen;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.baumovieapp.data.main_list.MovieModel;
import com.example.baumovieapp.databinding.ActivityMainBinding;
import com.example.baumovieapp.service.ClientMovie;
import com.example.baumovieapp.service.IRequest;
import com.example.baumovieapp.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> movieList = new MutableLiveData<>();

    public void getMoviesData(String movie){

        IRequest request = ClientMovie.getApiClient().create(IRequest.class);
        Call<MovieModel> call = request.getMovieList(Constants.KEY,movie);
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.isSuccessful()) {
                    Log.v("SEARCH_RES","the response" + response.body().toString());
                    List<MovieModel> data = response.body().getMovies();
                    movieList.postValue(data);
                }
                else{
                    Log.v("SEARCH_ERR","Error" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }


        });
    }
    public LiveData<List<MovieModel>> getMovieList() {
        return movieList;
    }
}
