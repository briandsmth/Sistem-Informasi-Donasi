package com.briand.sehatdonasionline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttondaftar;
    private EditText editTextemail;
    private EditText editTextpass;
    private TextView textViewlogin;


    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        }

        progressDialog = new ProgressDialog(this);

        buttondaftar = (Button) findViewById(R.id.btn_register);

        editTextemail = (EditText) findViewById(R.id.edit_email);
        editTextpass = (EditText) findViewById(R.id.edit_pass);
        textViewlogin = (TextView) findViewById(R.id.text_signin);

        buttondaftar.setOnClickListener(this);
        textViewlogin.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void registerUser(){
        String email = editTextemail.getText().toString().trim();
        String password = editTextpass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Silahkan masukkan email", Toast.LENGTH_SHORT).show();
            return;

        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Silahkan masukkan password", Toast.LENGTH_SHORT).show();
            return;
        } else {

            buatakunuser(email, password);
        }
    }

    private void buatakunuser(String email, String password) {
        progressDialog.setMessage("Sedang di Register...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();

                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("nama", "");
                            hashMap.put("alamat", "");
                            hashMap.put("phone", "");
                            hashMap.put("image", "");
                            hashMap.put("cover", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference databaseReference = database.getReference("Users");

                            databaseReference.child(uid).setValue(hashMap);

                            Toast.makeText(Register_Activity.this, "Registrasi Berhasil.. Sedang Coba Login", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(Register_Activity.this, "Registrasi Belum Berhasil.. Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view == buttondaftar){
            registerUser();
        }

        if(view == textViewlogin){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
