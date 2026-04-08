package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgPoster;
    private TextView tvTitle, tvGenre, tvRating, tvDescription;
    private Button btnBookTicket;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        getIntentData();
        displayMovieInfo();
        setupListeners();
    }

    private void initViews() {
        imgPoster = findViewById(R.id.imgDetailPoster);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvGenre = findViewById(R.id.tvDetailGenre);
        tvRating = findViewById(R.id.tvDetailRating);
        tvDescription = findViewById(R.id.tvDetailDescription);
        btnBookTicket = findViewById(R.id.btnBookTicket);
    }

    private void getIntentData() {
        if (getIntent().hasExtra("movie_data")) {
            movie = (Movie) getIntent().getSerializableExtra("movie_data");
        }
    }

    private void displayMovieInfo() {
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvGenre.setText(movie.getGenre());
            tvRating.setText("⭐ " + movie.getRating());
            tvDescription.setText(movie.getDescription());

            Glide.with(this)
                    .load(movie.getPosterUrl())
                    .into(imgPoster);
        }
    }

    private void setupListeners() {
        btnBookTicket.setOnClickListener(v -> {
            // logic to go to BookingActivity or show toast
            Toast.makeText(this, "Booking for " + movie.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }
}
