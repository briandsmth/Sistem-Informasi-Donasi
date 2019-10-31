package com.briand.sehatdonasionline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonlogin;
    private EditText editEmail;
    private EditText editPass;
    private TextView textViewsingup;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = (EditText) findViewById(R.id.edit_email);
        editPass = (EditText) findViewById(R.id.edit_pass);
        buttonlogin = (Button) findViewById(R.id.btn_login);
        textViewsingup = (TextView) findViewById(R.id.text_signup);

        progressDialog = new ProgressDialog(this);

        buttonlogin.setOnClickListener(this);
        textViewsingup.setOnClickListener(this);
    }

    private void userlogin(){
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Silahkan masukkan email", Toast.LENGTH_SHORT).show();
            return;

        }if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Silahkan masukkan password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Sedang Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ItemsActivity.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonlogin){
            userlogin();
        }

        if (view == textViewsingup){
            finish();
            startActivity(new Intent(this, Register_Activity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            Intent home = new Intent(LoginActivity.this, ItemsActivity.class);
            startActivity(home);
            finish();
        }
    }
}
