package com.example.fixster2;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.fixster2.databinding.ActivityDetailBinding;
import com.example.fixster2.databinding.ActivityMainBinding;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class DetailActivity extends AppCompatActivity {

    // Views/UI Components

    ImageView imageView;
    ImageView miniImage;
    TextView title;
    TextView overviewD;
    TextView date;
    TextView votes;
    Context context;
    RatingBar rate;
    TextView trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        context = this;
        miniImage = binding.posterV;
        title = binding.titleDet;
        overviewD = binding.overDet;
        date = binding.dateDet;
        votes = binding.votesDet;
        rate = binding.ratingMov;
        trailer = binding.Trailer;

        String imageURL;
        String miniURL;
        int placeholder;
        int miniPlaceH;

        title.setText(getIntent().getStringExtra(MainActivity.TITLE_KEY));
        overviewD.setText(getIntent().getStringExtra(MainActivity.OVERVIEW_KEY));
        date.setText(getIntent().getStringExtra(MainActivity.DATE_KEY));
        votes.setText(getIntent().getStringExtra(MainActivity.VOTES_KEY) + " votes");
        float rating = (Float.valueOf(getIntent().getStringExtra(MainActivity.RATE_KEY)) * 5) / 10; // Converting the rate from a 10 scale to a 5 scale
        rate.setRating(rating);
        trailer.setText("Trailer: " + getIntent().getStringExtra(MainActivity.TRAILER_KEY));
        Linkify.addLinks(trailer, Linkify.WEB_URLS);

        // if phone is in landscape then imageURL = backdropImage
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageURL = getIntent().getStringExtra(MainActivity.BACK_KEY);
            miniURL = getIntent().getStringExtra(MainActivity.POSTER_KEY);
            placeholder = R.drawable.flicks_backdrop_placeholder;
            miniPlaceH = R.drawable.flicks_movie_placeholder;
        } else {
            imageView = binding.imageDetail;
            imageURL = getIntent().getStringExtra(MainActivity.BACK_KEY);
            miniURL = getIntent().getStringExtra(MainActivity.POSTER_KEY);
            placeholder = R.drawable.flicks_backdrop_placeholder;
            miniPlaceH = R.drawable.flicks_movie_placeholder;
            Glide.with(context).load(imageURL).placeholder(placeholder).into(imageView);
        }
        //else imageURL = poster image
        Glide.with(context).load(miniURL).placeholder(miniPlaceH).transform(new RoundedCorners(50)).into(miniImage);

    }
}
