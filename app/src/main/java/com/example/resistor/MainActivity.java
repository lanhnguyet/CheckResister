package com.example.resistor;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (findViewById(R.id.secondfragment)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }

            SecondFragment secondFragment=new SecondFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.secondfragment, secondFragment, null).commit();
        }

        if (findViewById(R.id.thirdFragment)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }

            ThirdFragment thirdFragment=new ThirdFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.thirdFragment, thirdFragment,null).commit();
        }




        BottomNavigationView bottomNav= findViewById(R.id.Bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.Frament_container,
                new FirstFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrament= null;

                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFrament = new FirstFragment();
                            break;
                        case R.id.navigation_info:
                            selectedFrament = new SecondFragment();
                            break;
                        case R.id.menu_setting:
                            selectedFrament = new ThirdFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frament_container, selectedFrament).commit();
                    return true;
                }
            };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}