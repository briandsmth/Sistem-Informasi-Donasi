package com.briand.sehatdonasionline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombot;

    private ImageView login_menu, donate_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tampil_animasi();

        login_menu = (ImageView)findViewById(R.id.login_menu);
        donate_menu = (ImageView)findViewById(R.id.news_donate);

        login_menu.setOnClickListener(this);
        donate_menu.setOnClickListener(this);

    }

    private void tampil_animasi() {
        frombot = AnimationUtils.loadAnimation(this, R.anim.frombot);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(1000).setStartDelay(300);
        clover.animate().alpha(0).setDuration(1000).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(1000).setStartDelay(300);

        texthome.startAnimation(frombot);
        menus.startAnimation(frombot);
    }

    @Override
    public void onClick(View view) {
        if (view == login_menu){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(view == donate_menu){
            finish();
            startActivity(new Intent(this, Register_Activity.class));
        }
    }
}