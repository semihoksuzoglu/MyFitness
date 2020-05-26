package com.semihoksuzoglu.myfitness2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.semihoksuzoglu.myfitness2.R;

public class MainActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        BottomNavigationView bottomNav= findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SportFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;

            switch (item.getItemId())
            {
                case R.id.nav_sport:
                    selectedFragment=new SportFragment();
                    break;
                case R.id.nav_water:
                    selectedFragment=new WaterFragment(context, getSupportFragmentManager());
                    break;
                case R.id.nav_kcal:
                    selectedFragment=new KcalFragment(context, getSupportFragmentManager());
                    break;
                case R.id.nav_profile:
                    selectedFragment=new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}
