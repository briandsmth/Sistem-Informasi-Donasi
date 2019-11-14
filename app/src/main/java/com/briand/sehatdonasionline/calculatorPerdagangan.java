package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class calculatorPerdagangan extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE_RESULT = "state_result";

    EditText edt_modal, edt_untung, edt_hutang, edt_rugi, edt_piutang;
    Button btn_hit, btn_bayar;
    TextView tv_hasil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_perdagangan);

        edt_modal = findViewById(R.id.input_modal);
        edt_untung = findViewById(R.id.input_keuntungan);
        edt_hutang = findViewById(R.id.input_hutang);
        edt_rugi = findViewById(R.id.input_kerugian);
        edt_piutang = findViewById(R.id.input_piutang);

        tv_hasil = findViewById(R.id.hasil_perdagangan);

        btn_hit = findViewById(R.id.btn_hit_perdagangan);
        btn_bayar = findViewById(R.id.btn_bayar_perdagangan);

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calculatorPerdagangan.this, donateTransaksi.class);
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
        if (v.getId() == R.id.btn_hit_perdagangan){
            String inputModal = edt_modal.getText().toString().trim();
            String inputKeuntungan = edt_untung.getText().toString().trim();
            String inputHutang = edt_hutang.getText().toString().trim();
            String inputKerugian = edt_rugi.getText().toString().trim();
            String inputPiutang = edt_piutang.getText().toString().trim();
            Double modal = Double.parseDouble(inputModal);
            Double keuntungan = Double.parseDouble(inputKeuntungan);
            Double hutang = Double.parseDouble(inputHutang);
            Double kerugian = Double.parseDouble(inputKerugian);
            Double piutang = Double.parseDouble(inputPiutang);
            int nishab = 56525000;

            double hit = (modal + keuntungan  + piutang)-(hutang + kerugian);

            boolean isEmptyFields = false;

            if (TextUtils.isEmpty(inputModal)){
                isEmptyFields = true;
                edt_modal.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputKeuntungan)){
                isEmptyFields = true;
                edt_untung.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputHutang)){
                isEmptyFields = true;
                edt_hutang.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputKerugian)){
                isEmptyFields = true;
                edt_rugi.setError("Field ini tidak boleh kosong");
            }
            if (TextUtils.isEmpty(inputPiutang)){
                isEmptyFields = true;
                edt_piutang.setError("Field ini tidak boleh kosong");
            }

            if (!isEmptyFields && hit < nishab ){

                tv_hasil.setText("Anda belum wajib membayar zakat");

            }else{

                double hit2 = ((modal + keuntungan  + piutang)-(hutang + kerugian)) * 0.025;
                tv_hasil.setText(String.valueOf(hit2));

            }

        }

    }
}
