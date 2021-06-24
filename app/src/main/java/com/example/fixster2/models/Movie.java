package com.example.fixster2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    // Initializing the variables to reference the UI Components (views)
    String posterPath;
    String title;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException { // Constructor for a movie object that receives a JSON object
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");

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

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
