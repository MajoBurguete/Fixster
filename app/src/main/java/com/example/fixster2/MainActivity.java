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
import com.example.fixster2.databinding.ActivityDetailBinding;
import com.example.fixster2.databinding.ActivityMainBinding;
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
    public static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/";
    public static final String TAG = "MainActivity";

    public static final String TITLE_KEY = "movie_title";
    public static final String OVERVIEW_KEY = "movie_overview";
    public static final String POSTER_KEY = "poster_path";
    public static final String BACK_KEY = "backdrop_path";
    public static final String DATE_KEY = "release_date";
    public static final String RATE_KEY = "5";
    public static final String VOTES_KEY = "10";
    public static final String TRAILER_KEY = "link";
    public String link;

    //List with movie objects
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);  //Setting the main screen
        RecyclerView rvMovies = findViewById(R.id.rvMovies); // Obtaining the UI Component (Recycler view) with the id
        movies = new ArrayList<>(); // Array to save the movies
        AsyncHttpClient client = new AsyncHttpClient();



        // Create on click listener
        MovieAdapter.OnClickListener onClickListener = new MovieAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d(TAG, "onItemClicked: Image was clicked!");
                //Create the new activity
                Intent actI = new Intent(MainActivity.this, DetailActivity.class);
                // Obtain trailer link

                // Pass the data
                actI.putExtra(TITLE_KEY,movies.get(position).getTitle());
                actI.putExtra(OVERVIEW_KEY, movies.get(position).getOverview());
                actI.putExtra(POSTER_KEY, movies.get(position).getPosterPath());
                actI.putExtra(BACK_KEY, movies.get(position).getBackdropPath());
                actI.putExtra(DATE_KEY, movies.get(position).getDate());
                actI.putExtra(RATE_KEY, String.valueOf(movies.get(position).getRate()));
                actI.putExtra(VOTES_KEY, String.valueOf(movies.get(position).getVotes()));

                String URL = TRAILER_URL + movies.get(position).getId()+"/videos?api_key=c64f0bdb8d179732c61c96eda2965a71&language=en-US";

                client.get(URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;
                        int positionJson = 0;
                        try {
                            JSONArray results = jsonObject.getJSONArray("results"); // "results" it's the name of the array that the json object contains
                            for (int j=0; j < results.length(); j++){
                                if (results.getJSONObject(j).get("name") == "Official Trailer"){
                                    positionJson = j;
                                    break;
                                }
                            }
                            JSONObject result = results.getJSONObject(positionJson);
                            link = "https://www.youtube.com/watch?v=" + result.get("key").toString();
                            actI.putExtra(TRAILER_KEY, link);
                            startActivity(actI);
                        } catch (JSONException e) {
                            Log.e(TAG, "Hit Json Exception", e);
                        }

                    }

                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {

                    }
                });

            }
        };

        // Create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies, onClickListener);

        //Set the adapter on the recycler view which help us translate the data into UI Components
        rvMovies.setAdapter(movieAdapter);

        //Set layout manager on the recycler view which is the responsible for positioning item views as well
        // as determining the policy for when to recycle item views that are no longer visible to the user
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

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