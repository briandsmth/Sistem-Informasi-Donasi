package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class calculatorPenghasilan extends AppCompatActivity implements View.OnClickListener{

    private static final String STATE_RESULT = "state_result";

    EditText edt_penghasilan, edt_cicilan;
    Button btn_hit, btnbayar;
    TextView tv_hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_penghasilan);

        edt_penghasilan = findViewById(R.id.penghasilan);
        edt_cicilan = findViewById(R.id.cicilan);
        btn_hit = findViewById(R.id.btn_hit_penghasilan);
        btnbayar = findViewById(R.id.btn_bayar_penghasilan);
        tv_hasil = findViewById(R.id.hasil_penghasilan);

        btnbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calculatorPenghasilan.this, donateTransaksi.class);
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
        if (v.getId() == R.id.btn_hit_penghasilan){
            String inputCicilan = edt_cicilan.getText().toString().trim();
            String inputPenghasilan = edt_penghasilan.getText().toString().trim();
            Double nominal = Double.parseDouble(inputPenghasilan);
            Double cicilan = Double.parseDouble(inputCicilan);
            int nishab = 6400000;

            double hit = (nominal - cicilan);

            boolean isEmptyFields = false;

            if (TextUtils.isEmpty(inputPenghasilan)){
                isEmptyFields = true;
                edt_penghasilan.setError("Field ini tidak boleh kosong");
            }

            if (!isEmptyFields && hit < nishab ){

                tv_hasil.setText("Anda belum wajib membayar zakat");

            }else{


                double hit2 = (nominal - cicilan) * 0.025;
                tv_hasil.setText(String.valueOf(hit2));

            }

        }
    }
}
