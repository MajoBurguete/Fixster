package com.example.fixster2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    // Initializing the variables to reference the UI Components (views)
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String date;
    int id;
    float rate;
    int votes;

    public Movie(JSONObject jsonObject) throws JSONException { // Constructor for a movie object that receives a JSON object
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        date = jsonObject.getString("release_date");
        id = jsonObject.getInt("id");
        rate = Float.valueOf(String.valueOf(jsonObject.getDouble("vote_average")));
        votes = jsonObject.getInt("vote_count");


    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>(); // Array which will save a list of movie objects from a list of json objects
        for (int i=0; i< movieJsonArray.length(); i++){ // We iterate the list of json objects and add them to the list of movie objects
            movies.add(new Movie(movieJsonArray.getJSONObject(i))); // each json object is converted to a movie object and inserted into de movies list.
        }
        return movies;
    }

    // Getter for each attribute.

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getDate() { return date; }

    public int getId() { return id; }

    public float getRate() { return rate; }

    public int getVotes() { return votes; }
}
