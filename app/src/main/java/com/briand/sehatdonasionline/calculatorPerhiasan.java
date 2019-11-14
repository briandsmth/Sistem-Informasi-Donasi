package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class calculatorPerhiasan extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE_RESULT = "state_result";

    EditText edt_emas;
    Button btn_hit, btn_bayar;
    TextView tv_hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_perhiasan);

        edt_emas = findViewById(R.id.input_perhiasan);
        btn_hit = findViewById(R.id.btn_hit_perhiasan);
        btn_bayar = findViewById(R.id.btn_bayar_perhiasan);
        tv_hasil = findViewById(R.id.hasil_perhiasan);

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calculatorPerhiasan.this, donateTransaksi.class);
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
        if (v.getId() == R.id.btn_hit_perhiasan){
            String inputEmas = edt_emas.getText().toString().trim();
            Double emas = Double.parseDouble(inputEmas);
            int nishab = 56525000;
            double hit = emas * 665000;

            boolean isEmptyFields = false;

            if (TextUtils.isEmpty(inputEmas)){
                isEmptyFields = true;
                edt_emas.setError("Field ini tidak boleh kosong");
            }

            if (!isEmptyFields && hit < nishab ){

                tv_hasil.setText("Anda belum wajib membayar zakat");

            }else{

                tv_hasil.setText(String.valueOf(hit));

            }

        }
    }
}
