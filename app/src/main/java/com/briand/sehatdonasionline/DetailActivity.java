package com.briand.sehatdonasionline;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class DetailActivity extends AppCompatActivity {
    FirebaseAuth donasiAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    StorageReference storageReference;

    String storagePath = "Campaign_uploaded/";

    private ProgressBar uploadProggress;

    ProgressDialog pd;

    //permission constant
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE = 400;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    String profilePhoto;

    FloatingActionButton fab;

    TextView namaDetailTV, storyDetailTV, goalsDetailTV;
    ImageView victimDetailIV;

    Button btn_donate;

    private void initializeWidgets(){
        namaDetailTV = findViewById(R.id.nama_detail);
        storyDetailTV = findViewById(R.id.story_detail);
        goalsDetailTV = findViewById(R.id.goals_detail);
        victimDetailIV = findViewById(R.id.img_detail);
        uploadProggress = findViewById(R.id.proggressBarDetail);
        btn_donate = findViewById(R.id.btn_donate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializeWidgets();

        donasiAuth = FirebaseAuth.getInstance();
        user = donasiAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Campaign_uploaded");
        storageReference = getInstance().getReference();

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        fab = findViewById(R.id.fab);

        //ambil dari item activity
        Intent i = this.getIntent();
        String nama = i.getExtras().getString("NAME_KEY");
        String story = i.getExtras().getString("DESCRIPTION_KEY");
        String goals = i.getExtras().getString("GOALS_KEY");
        String imageURL = i.getExtras().getString("IMAGE_KEY");

        //Set data di xml Detail
        namaDetailTV.setText(nama);
        storyDetailTV.setText(story);
        goalsDetailTV.setText(goals);
        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.imgunvaible)
                .fit()
                .centerCrop()
                .into(victimDetailIV);

        pd = new ProgressDialog(DetailActivity.this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDonasiDialog();
            }
        });

        btn_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, donateTransaksi.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(DetailActivity.this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(DetailActivity.this, cameraPermission, STORAGE_REQUEST_CODE);
    }

    private void showEditDonasiDialog() {
        String option[] = {"Edit Profile Picture", "Edit Nama", "Edit Goals", "Edit Story"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

        builder.setTitle("Choose Action");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0){

                    pd.setMessage("Updating Profile Picture");
                    profilePhoto = "imageurl";
                    showImagePictDialog();

                }else if (which == 1){

                    pd.setMessage("Updating Profile Name");
                    showNameGoalsStoryUpdate("nama");

                }else if (which == 2){

                    pd.setMessage("Updating Profile Phone");
                    showNameGoalsStoryUpdate("goals");

                }else if (which == 3){

                    pd.setMessage("Updating Profile Address");
                    showNameGoalsStoryUpdate("story");

                }

            }
        });
        builder.create().show();

    }

    private void showNameGoalsStoryUpdate(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("Update " + key);

        LinearLayout linearLayout = new LinearLayout(DetailActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText editText = new EditText(DetailActivity.this);
        editText.setHint("Masukkan "+key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)){

                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(DetailActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }else {
                    Toast.makeText(DetailActivity.this, "Please Enter "+key, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showImagePictDialog() {
        String option[] = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

        builder.setTitle("Choose Action");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0){

                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        pickFromCamera();
                    }

                }else if (which == 1){

                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }

                }

            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(DetailActivity.this, "Tolong Aktifkan Permission Camera & Penyimpanan", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickFromGallery();
                    }else {
                        Toast.makeText(DetailActivity.this, "Tolong Aktifkan Permission Penyimpanan", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_REQUEST_CODE){
                image_uri = data.getData();

                uploadProfilePhoto(image_uri);
            }
            if (requestCode == IMAGE_PICK_CAMERA_REQUEST_CODE){

                uploadProfilePhoto(image_uri);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfilePhoto(final Uri uri) {

        String filePathAndName = storagePath+ ""+ profilePhoto +"" + user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        uploadProggress.setVisibility(View.VISIBLE);
        uploadProggress.setIndeterminate(true);
        storageReference2nd.putFile(uri)
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
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if (uriTask.isSuccessful()){
                            HashMap<String, Object> result = new HashMap<>();
                            result.put(profilePhoto, downloadUri.toString());

                            databaseReference.child(user.getUid()).updateChildren(result)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pd.dismiss();
                                            Toast.makeText(DetailActivity.this, "Image Berhasil di Update", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(DetailActivity.this, "Terjadi Kesalahan Update", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            pd.dismiss();
                            Toast.makeText(DetailActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                        uploadProggress.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                uploadProggress.setProgress((int) progress);
            }
        });

    }

    private void pickFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, IMAGE_PICK_GALLERY_REQUEST_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp description");
        //put image to uri
        image_uri = DetailActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //intent camera
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(camera, IMAGE_PICK_CAMERA_REQUEST_CODE);
    }


}
