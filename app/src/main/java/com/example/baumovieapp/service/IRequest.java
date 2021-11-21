package com.example.baumovieapp.service;

import com.example.baumovieapp.data.main_list.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRequest {

    @GET("search/movie")
    Call <MovieModel> getMovieList(
            @Query("api_key") String key,
            @Query("query") String query
            //@Query("page") String page
    );

    @GET("movie/{movie_id}")
    Call <MovieModel>getSpecificMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key

    );

}
