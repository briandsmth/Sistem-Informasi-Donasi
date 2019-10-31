package com.briand.sehatdonasionline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class startCampaign extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button btnupload, btnstartcampaign;

    private EditText namaEdit, storyEdit, goalsEdit;

    private ImageView chosenimg;

    private ProgressBar uploadProggress;
    private Uri mImageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_campaign);

        btnupload = findViewById(R.id.btn_victim);
        btnstartcampaign = findViewById(R.id.btn_campaign);
        namaEdit = findViewById(R.id.edit_nama);
        storyEdit = findViewById(R.id.edit_story);
        goalsEdit = findViewById(R.id.edit_goals);
        chosenimg = findViewById(R.id.victimImg);
        uploadProggress = findViewById(R.id.proggressBar);

        storageReference = FirebaseStorage.getInstance().getReference("Campaign_uploaded");
        databaseReference = FirebaseDatabase.getInstance().getReference("Campaign_uploaded");

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });

        btnstartcampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storageTask != null && storageTask.isInProgress()){
                    Toast.makeText(startCampaign.this, "Upload masih Dalam Proses",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                    Intent intent = new Intent(startCampaign.this, ItemsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void openFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(chosenimg);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if (mImageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            uploadProggress.setVisibility(View.VISIBLE);
            uploadProggress.setIndeterminate(true);
            storageTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProggress.setVisibility(View.VISIBLE);
                                    uploadProggress.setIndeterminate(false);
                                    uploadProggress.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(startCampaign.this, "Campaign Add Successful", Toast.LENGTH_SHORT).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("imageurl", String.valueOf(uri));
                                    hashMap.put("nama", namaEdit.getText().toString());
                                    hashMap.put("goals", goalsEdit.getText().toString());
                                    hashMap.put("story", storyEdit.getText().toString());

                                    String uploadId = databaseReference.push().getKey();

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    DatabaseReference databaseReference = database.getReference("Campaign_uploaded");

                                    databaseReference.child(uploadId).setValue(hashMap);

                                }
                            });

                            uploadProggress.setVisibility(View.INVISIBLE);
                            openImageActivity();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProggress.setVisibility(View.INVISIBLE);
                            Toast.makeText(startCampaign.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProggress.setProgress((int) progress);
                        }
                    });
        }else{
            Toast.makeText(this, "Kamu belum memilih file apapun", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImageActivity(){
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }
}
