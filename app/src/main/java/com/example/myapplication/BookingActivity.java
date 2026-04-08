package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Date;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Hàm thực hiện đặt vé: 
     * 1. Lưu vé vào collection "tickets"
     * 2. Cập nhật mảng "bookedSeats" của suất chiếu tương ứng
     * Sử dụng WriteBatch để đảm bảo tính toàn vẹn dữ liệu (cả 2 thành công hoặc không gì cả)
     */
    private void performBooking(String userId, Showtime showtime, List<String> selectedSeats, String movieTitle) {
        // Tính tổng tiền
        double totalPrice = selectedSeats.size() * showtime.getPrice();

        // Tạo đối tượng Ticket
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setShowtimeId(showtime.getId());
        ticket.setMovieTitle(movieTitle);
        ticket.setSeats(selectedSeats);
        ticket.setTotalPrice(totalPrice);
        ticket.setTimestamp(new Date());

        // Sử dụng WriteBatch để thực hiện nhiều thao tác đồng thời
        WriteBatch batch = db.batch();

        // 1. Tham chiếu đến document mới trong collection "tickets"
        DocumentReference ticketRef = db.collection("tickets").document();
        ticket.setId(ticketRef.getId()); // Gán ID tự sinh cho ticket object
        batch.set(ticketRef, ticket);

        // 2. Tham chiếu đến document của suất chiếu trong collection "showtimes"
        DocumentReference showtimeRef = db.collection("showtimes").document(showtime.getId());
        
        // Cập nhật mảng bookedSeats bằng cách thêm các ghế mới vào (arrayUnion)
        // Lưu ý: arrayUnion nhận vào các phần tử rời rạc, nên ta cast sang mảng
        batch.update(showtimeRef, "bookedSeats", FieldValue.arrayUnion(selectedSeats.toArray()));

        // Thực thi Batch
        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(BookingActivity.this, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển hướng hoặc đóng màn hình
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("BookingError", "Lỗi khi đặt vé", e);
                    Toast.makeText(BookingActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
