package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class zakatActivity extends AppCompatActivity {

    private String title = "ZAKAT";

    Button btn_simpanan, btn_penghasilan, btn_perhiasan, btn_perdagangan, btn_fitrah;
    TextView tv_calsimpanan, tv_calpenghasilan, tv_calperhiasan, tv_calperdagangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat);
        setActionBarTitle(title);

        btn_simpanan = findViewById(R.id.btn_simpanan);
        btn_penghasilan = findViewById(R.id.btn_pengahasilan);
        btn_perhiasan = findViewById(R.id.btn_perhiasan);
        btn_perdagangan = findViewById(R.id.btn_perdagangan);
        btn_fitrah = findViewById(R.id.btn_fitrah);

        tv_calsimpanan = findViewById(R.id.cal_simpanan);
        tv_calpenghasilan = findViewById(R.id.cal_penghasilan);
        tv_calperhiasan = findViewById(R.id.cal_Emas);
        tv_calperdagangan = findViewById(R.id.cal_perdagangan);

        btn_simpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zakatActivity.this, donateTransaksi.class);
                startActivity(intent);
            }
        });

        btn_penghasilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zakatActivity.this, donateTransaksi.class);
                startActivity(intent);
            }
        });

        btn_perhiasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zakatActivity.this, donateTransaksi.class);
                startActivity(intent);
            }
        });

        btn_perdagangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zakatActivity.this, donateTransaksi.class);
                startActivity(intent);
            }
        });

        btn_fitrah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zakatActivity.this, donateTransaksi.class);
                startActivity(intent);
            }
        });

        tv_calsimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(zakatActivity.this, calculatorSimpanan.class);
                startActivity(intent);
            }
        });

        tv_calpenghasilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(zakatActivity.this, calculatorPenghasilan.class);
                startActivity(intent);
            }
        });

        tv_calperhiasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(zakatActivity.this, calculatorPerhiasan.class);
                startActivity(intent);
            }
        });

        tv_calperdagangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(zakatActivity.this, calculatorPerdagangan.class);
                startActivity(intent);
            }
        });



    }


    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }
}
