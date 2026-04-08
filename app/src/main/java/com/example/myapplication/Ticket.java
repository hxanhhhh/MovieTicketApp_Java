package com.example.myapplication;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Ticket implements Serializable {
    private String id;
    private String userId;
    private String showtimeId;
    private String movieTitle;
    private List<String> seats;
    private double totalPrice;
    private Date timestamp;

    public Ticket() {
        // Required for Firestore
    }

    public Ticket(String id, String userId, String showtimeId, String movieTitle, List<String> seats, double totalPrice, Date timestamp) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.movieTitle = movieTitle;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public List<String> getSeats() { return seats; }
    public void setSeats(List<String> seats) { this.seats = seats; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
