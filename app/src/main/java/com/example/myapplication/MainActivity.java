package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    private List<Movie> fullMovieList; // Để dùng cho search
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initFirestore();
        fetchMovies();
        setupSearch();
    }

    private void initViews() {
        rvMovies = findViewById(R.id.rvMovies);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);

        movieList = new ArrayList<>();
        fullMovieList = new ArrayList<>();
        adapter = new MovieAdapter(this, movieList);

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setAdapter(adapter);
    }

    private void initFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    private void fetchMovies() {
        progressBar.setVisibility(View.VISIBLE);

        db.collection("movies")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.GONE);
                    movieList.clear();
                    fullMovieList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Movie movie = document.toObject(Movie.class);
                        movie.setId(document.getId());
                        movieList.add(movie);
                        fullMovieList.add(movie);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Log.e("FirestoreError", "Error fetching movies", e);
                    Toast.makeText(MainActivity.this, "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovies(newText);
                return true;
            }
        });
    }

    private void filterMovies(String query) {
        movieList.clear();
        if (query.isEmpty()) {
            movieList.addAll(fullMovieList);
        } else {
            for (Movie movie : fullMovieList) {
                if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    movieList.add(movie);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
