package com.briand.sehatdonasionline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.briand.sehatdonasionline.R.id.mRecyclerView;

public class ItemsActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {

    BottomNavigationView bottomNavigationView;

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<victimModel> mVictim;

    private void openDetailActivity(String[] data) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("NAME_KEY", data[0]);
        intent.putExtra("DESCRIPTION_KEY", data[1]);
        intent.putExtra("GOALS_KEY", data[2]);
        intent.putExtra("IMAGE_KEY", data[3]);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        final ActionBar actionBar = getSupportActionBar();

        bottomNavigationView = findViewById(R.id.botNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_home:

                        break;
                    case R.id.item_startCam:
                        Intent campaign = new Intent(ItemsActivity.this, startCampaign.class);
                        startActivity(campaign);
                        break;
                    case R.id.item_profile:
                        actionBar.setTitle("Profile View");
                        Intent profileView = new Intent(ItemsActivity.this, profile_Activity.class);
                        startActivity(profileView);
                        break;
                    case R.id.item_zakat:
                        actionBar.setTitle("Zakat");
                        Intent zakatView = new Intent(ItemsActivity.this, zakatActivity.class);
                        startActivity(zakatView);
                        break;
                }
                return true;
            }
        });

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        mVictim = new ArrayList<>();
        mAdapter = new RecyclerAdapter(ItemsActivity.this, mVictim);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ItemsActivity.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Campaign_uploaded");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mVictim.clear();
                for (DataSnapshot victimSnapshot : dataSnapshot.getChildren()) {
                    victimModel upload = victimSnapshot.getValue(victimModel.class);
                    upload.setKey(victimSnapshot.getKey());
                    mVictim.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ItemsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        victimModel clickedVictim = mVictim.get(position);
        String[] victimData = {clickedVictim.getNama(), clickedVictim.getStory(), clickedVictim.getGoals(), clickedVictim.getImageurl()};
        openDetailActivity(victimData);

    }

}
