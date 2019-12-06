package com.briand.sehatdonasionline;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class paymentView extends AppCompatActivity {

    private TextView tv_bank, tv_rupiah, tv_virtual, tv_tanggal;
    String bank, rupiah, virtual, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_view);

        tv_bank = findViewById(R.id.bank);
        tv_rupiah = findViewById(R.id.get_rupiah);
        tv_virtual = findViewById(R.id.get_virtual);
        tv_tanggal = findViewById(R.id.tanggal);

        bank = getIntent().getExtras().getString("bank_key");
        rupiah = getIntent().getExtras().getString("donasi_key");
        virtual = getIntent().getExtras().getString("virtual_key");
        tanggal = getIntent().getExtras().getString("tanggal_key");

        tv_bank.setText(bank);
        tv_virtual.setText(virtual);
        tv_rupiah.setText(rupiah);
        tv_tanggal.setText(tanggal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent myIntent = new Intent(paymentView.this, ItemsActivity.class);
                startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
