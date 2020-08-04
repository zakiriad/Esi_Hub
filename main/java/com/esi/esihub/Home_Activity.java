package com.esi.esihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.esi.esihub.CV_Details.Resume_fragment;
import com.esi.esihub.Helper_classes.Resume;
import com.esi.esihub.Home_Fragments.Actualite_fragment;
import com.esi.esihub.Home_Fragments.Formation_fragment;
import com.esi.esihub.Home_Fragments.Job_fragment;
import com.esi.esihub.Home_Fragments.Profil_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.esi.esihub.R.*;
import static com.esi.esihub.R.string.*;
import static com.esi.esihub.R.string.open;

public class Home_Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home_);




        drawerLayout = findViewById(id.HomeActivity);

        NavigationView navigationView = findViewById(id.navDrawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, open, close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setupDrawerContent(navigationView);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Actualite_fragment());

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id.fragmentLay, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = new Actualite_fragment();
                    switch (item.getItemId()){
                        case id.Job_menu_item:
                            fragment = new Job_fragment();
                            loadFragment(fragment);
                            return true;
                        case id.Formation_menu_item:
                            fragment= new Formation_fragment();
                            loadFragment(fragment);
                            return true;
                    }
                    try{
                        loadFragment(fragment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            };
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void selectItemDrawer(MenuItem item){
        Fragment fragment = null;

        Class fragmentClass = Actualite_fragment.class;
        switch (item.getItemId()){
            case id.Logout_menu_item:
                try{
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                }

            case id.Profil_menu_item:
                fragmentClass = Profil_fragment.class;
                break;
            case id.CV_menu_item:
                fragmentClass = Resume_fragment.class;
                break;

            default:
                break;


        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLay, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });

    }
}