package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Showtime implements Serializable {
    private String id;
    private String movieId;
    private String date;
    private String time;
    private double price;
    private List<String> bookedSeats;

    public Showtime() {
        this.bookedSeats = new ArrayList<>();
    }

    public Showtime(String id, String movieId, String date, String time, double price, List<String> bookedSeats) {
        this.id = id;
        this.movieId = movieId;
        this.date = date;
        this.time = time;
        this.price = price;
        this.bookedSeats = bookedSeats != null ? bookedSeats : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(List<String> bookedSeats) { this.bookedSeats = bookedSeats; }
}
