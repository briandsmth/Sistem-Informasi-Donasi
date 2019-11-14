package com.briand.sehatdonasionline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class donateTransaksi extends AppCompatActivity {

    private EditText edt_payment;
    private Spinner method;
    private Button btn_transaksi;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_transaksi);

        edt_payment = findViewById(R.id.jumlah_donasi);
        method = findViewById(R.id.method_pay);
        btn_transaksi = findViewById(R.id.transaksi);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Transaksi_Donasi");

        btn_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donasi();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Transaksi_Donasi").child(uid);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String bank = dataSnapshot.child("method").getValue().toString();
                        String donasi = dataSnapshot.child("payment").getValue().toString();
                        String virtual = dataSnapshot.child("uid").getValue().toString();
                        String tanggal = dataSnapshot.child("tanggal").getValue().toString();

                        Intent next = new Intent(donateTransaksi.this, paymentView.class);
                        next.putExtra("bank_key", bank);
                        next.putExtra("donasi_key", donasi);
                        next.putExtra("virtual_key", virtual);
                        next.putExtra("tanggal_key", tanggal);
                        startActivity(next);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void donasi(){
        String payment = edt_payment.getText().toString().trim();
        String payment_method = method.getSelectedItem().toString();

        if (TextUtils.isEmpty(payment)){
            Toast.makeText(this, "Silahkan masukkan jumlah donasi", Toast.LENGTH_SHORT).show();
            return;

        }else if (TextUtils.isEmpty(payment_method)){
            Toast.makeText(this, "Silahkan pilih metode pembayaran", Toast.LENGTH_SHORT).show();
            return;
        } else {

            transaksi();
        }
    }

    private void transaksi() {

        String email = user.getEmail();
        String uid = user.getUid();
        String payment_method = method.getSelectedItem().toString();
        String payment = edt_payment.getText().toString();

        HashMap<Object, String> hashMap = new HashMap<>();

        hashMap.put("email", email);
        hashMap.put("uid", generateVirtual(16));
        hashMap.put("payment", payment);
        hashMap.put("method", payment_method);
        hashMap.put("tanggal", getDateToday());

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Transaksi_Donasi");

        databaseReference.child(uid).setValue(hashMap);
    }

    private String generateVirtual(int lenght){
        char[] chars = "6437729834389237492837498365628376473298".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int a = 0; a < lenght; a++){
            char virtual =chars[random.nextInt(chars.length)];
            stringBuilder.append(virtual);
        }
        return stringBuilder.toString();
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }

}
