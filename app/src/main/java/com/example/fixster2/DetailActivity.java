package com.example.fixster2;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;


public class DetailActivity extends AppCompatActivity {

    // Views/UI Components

    ImageView imageView;
    ImageView miniImage;
    TextView title;
    TextView overviewD;
    Context context;
    RatingBar rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;
        imageView = findViewById(R.id.imageDetail);
        miniImage = findViewById(R.id.posterV);
        title = findViewById(R.id.titleDet);
        overviewD = findViewById(R.id.overDet);
        rate = findViewById(R.id.ratingMov);


        String imageURL;
        String miniURL;
        int placeholder;
        int miniPlaceH;

        title.setText(getIntent().getStringExtra(MainActivity.TITLE_KEY));
        overviewD.setText(getIntent().getStringExtra(MainActivity.OVERVIEW_KEY));
        float rating = (Float.valueOf(getIntent().getStringExtra(MainActivity.RATE_KEY)) * 5) / 10; // Converting the rate from a 10 scale to a 5 scale
        rate.setRating(rating);
        Log.d("DETAIL", "onCreate Rating: " + getIntent().getStringExtra(MainActivity.RATE_KEY));

        // if phone is in landscape then imageURL = backdropImage
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageURL = getIntent().getStringExtra(MainActivity.BACK_KEY);
            miniURL = getIntent().getStringExtra(MainActivity.POSTER_KEY);
            placeholder = R.drawable.flicks_backdrop_placeholder;
            miniPlaceH = R.drawable.flicks_movie_placeholder;
        } else {
            imageURL = getIntent().getStringExtra(MainActivity.BACK_KEY);
            miniURL = getIntent().getStringExtra(MainActivity.POSTER_KEY);
            placeholder = R.drawable.flicks_backdrop_placeholder;
            miniPlaceH = R.drawable.flicks_movie_placeholder;
        }
        //else imageURL = poster image
        Glide.with(context).load(imageURL).placeholder(placeholder).into(imageView);
        Glide.with(context).load(miniURL).placeholder(miniPlaceH).transform(new RoundedCorners(30)).into(miniImage);

    }
}
