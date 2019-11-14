package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class calculatorSimpanan extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE_RESULT = "state_result";

    EditText edt_simpanan;
    Button btn_hit, btn_bayar;
    TextView tv_hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_simpanan);

        edt_simpanan = findViewById(R.id.saldo);
        tv_hasil = findViewById(R.id.hasil);
        btn_hit = findViewById(R.id.btn_hit_simpanan);
        btn_bayar = findViewById(R.id.btn_bayar_simpanan);

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calculatorSimpanan.this, donateTransaksi.class);
                startActivity(intent);
            }
        });


        btn_hit.setOnClickListener(this);
        if (savedInstanceState != null){
            String result = savedInstanceState.getString(STATE_RESULT);
            tv_hasil.setText(result);
        }


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_hit_simpanan){
            String inputSaldo = edt_simpanan.getText().toString().trim();
            Double saldo = Double.parseDouble(inputSaldo);
            int nishab = 56525000;

            boolean isEmptyFields = false;

            if (TextUtils.isEmpty(inputSaldo)){
                isEmptyFields = true;
                edt_simpanan.setError("Field ini tidak boleh kosong");
            }

            if (!isEmptyFields && saldo < nishab ){

                tv_hasil.setText("Anda belum wajib membayar zakat");

            }else{

                double hit = 0.025 * saldo;

                tv_hasil.setText(String.valueOf(hit));

            }

        }

    }
}
