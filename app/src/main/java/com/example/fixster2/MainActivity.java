package com.example.fixster2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fixster2.adapters.MovieAdapter;
import com.example.fixster2.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // API Request link
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=c64f0bdb8d179732c61c96eda2965a71";
    public static final String TAG = "MainActivity";

    public static final String TITLE_KEY = "movie_title";
    public static final String OVERVIEW_KEY = "movie_overview";
    public static final String POSTER_KEY = "poster_path";
    public static final String BACK_KEY = "backdrop_path";
    public static final String DATE_KEY = "release_date";
    public static final String RATE_KEY = "5";
    public static final String VOTES_KEY = "10";

    //List with movie objects
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Setting the main screen
        RecyclerView rvMovies = findViewById(R.id.rvMovies); // Obtaining the UI Component (Recycler view) with the id
        movies = new ArrayList<>(); // Array to save the movies

        // Create on click listener
        MovieAdapter.OnClickListener onClickListener = new MovieAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                //Create the new activity
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                // Pass the data
                i.putExtra(TITLE_KEY,movies.get(position).getTitle());
                i.putExtra(OVERVIEW_KEY, movies.get(position).getOverview());
                i.putExtra(POSTER_KEY, movies.get(position).getPosterPath());
                i.putExtra(BACK_KEY, movies.get(position).getBackdropPath());
                i.putExtra(DATE_KEY, movies.get(position).getDate());
                i.putExtra(RATE_KEY, String.valueOf(movies.get(position).getRate()));
                i.putExtra(VOTES_KEY, String.valueOf(movies.get(position).getVotes()));
                Log.d(TAG, "onItemClicked: " + String.valueOf(movies.get(position).getRate()));
                startActivity(i);

            }
        };

        // Create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies, onClickListener);

        //Set the adapter on the recycler view which help us translate the data into UI Components
        rvMovies.setAdapter(movieAdapter);

        //Set layout manager on the recycler view which is the responsible for positioning item views as well
        // as determining the policy for when to recycle item views that are no longer visible to the user
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        // Makes the request to the movie API
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() { // JsonHttpResponseHandler it's use since the api returns a JSON object
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results"); // "results" it's the name of the array that the json object contains
                    movies.addAll(Movie.fromJsonArray(results)); // Adding all the json objects to the movie array
                    movieAdapter.notifyDataSetChanged(); // In case the data changes
                    Log.i(TAG, "Results: " + results.toString());
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit Json Exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }
}