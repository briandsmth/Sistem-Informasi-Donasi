package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView namaDetailTV, storyDetailTV, goalsDetailTV;
    ImageView victimDetailIV;

    private void initializeWidgets(){
        namaDetailTV = findViewById(R.id.nama_detail);
        storyDetailTV = findViewById(R.id.story_detail);
        goalsDetailTV = findViewById(R.id.goals_detail);
        victimDetailIV = findViewById(R.id.img_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializeWidgets();
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
    }
}
