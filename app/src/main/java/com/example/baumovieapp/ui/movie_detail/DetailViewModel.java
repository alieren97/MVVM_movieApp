package com.example.baumovieapp.ui.movie_detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.baumovieapp.data.main_list.MovieModel;
import com.example.baumovieapp.service.ClientMovie;
import com.example.baumovieapp.service.IRequest;
import com.example.baumovieapp.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel{



        private MutableLiveData<MovieModel> movieDetail = new MutableLiveData<>();


        public void getMovieDetail(int movieId) {
            IRequest request = ClientMovie.getApiClient().create(IRequest.class);
            Call<MovieModel> call = request.getSpecificMovie(movieId, Constants.KEY);
            call.enqueue(new Callback<MovieModel>() {
                @Override
                public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                    if (response.isSuccessful()) {
                        Log.d("DETAIL_RES", "onResponse: ");
                        System.out.println(response.body());
                        movieDetail.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<MovieModel> call, Throwable t) {
                    Log.d("DETAIL_FAIL", "onFailure: ");
                }
            });
        }


        public LiveData<MovieModel> getMovieDetaill() {
            return movieDetail;
        }

}
