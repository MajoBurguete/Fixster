package com.example.fixster2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.fixster2.R;
import com.example.fixster2.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

// An adapter class always inherits the RecyclerView.Adapter and for this we have to define a view holder
// which describes and provides access to all the views within each item row.
public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    // Constructor for a movie adapter that receives the context of the view(? and list of movie objects
    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout from XML and returning the holder.
    // The process of loading an XML file and turning that into objects (view) in an activity is called inflation.

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) { // Returns a ViewHolder that contains the UI components  in the item_movie.xml
        Log.d("MovieAdapter", "OnCreateViewHolder");
        // Creates the view object that contains the UI Components (item_movie xml)
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // Converts the view into a view holder
        return new ViewHolder(movieView);
    }

    //Involves populating data into the item through holder

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "OnBindViewHolder"+position);
        // Get the movie at the passed position in the movies list
        Movie movie = movies.get(position);

        //Bind the data into the view holder, sets every view/UI Component of the item_movie.xml to the data of the corresponding movie (image, overview and title)
        holder.bind(movie);
    }

    // Returns the total count of items in the list

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // Variables that will save the view/UI Components

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        // Constructor for the view holder tht receives a view object and saves each view/ UI Component
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        // Receives a movie object and edits the view/UI Component with the info of the movie object

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            int radius = 10;
            int margin = 0;
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }
    }
}
