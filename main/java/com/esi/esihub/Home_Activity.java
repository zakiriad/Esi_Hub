package com.esi.esihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esi.esihub.CV_Details.Resume_fragment;
import com.esi.esihub.Etapes.Import_Documents_fragment;
import com.esi.esihub.Etapes.Suivie_etape_physique_fragment;
import com.esi.esihub.Etapes.Verification_id_fragment;
import com.esi.esihub.Helper_classes.User;
import com.esi.esihub.Home_Fragments.Actualite_fragment;
import com.esi.esihub.Home_Fragments.Formation_fragment;
import com.esi.esihub.Home_Fragments.Job_fragment;
import com.esi.esihub.Home_Fragments.Profil_fragment;
import com.esi.esihub.Home_Fragments.Rating_fragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.esi.esihub.R.*;
import static com.esi.esihub.R.string.*;
import static com.esi.esihub.R.string.open;

public class Home_Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private DatabaseReference UserProfileReference;
    private ImageView Photo_header;
    private int Niveau, Etape = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if(FirebaseAuth.getInstance().getCurrentUser() != null){
                super.onCreate(savedInstanceState);
                setContentView(layout.activity_home_);


                drawerLayout = findViewById(id.HomeActivity);
                UserProfileReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                NavigationView navigationView = findViewById(id.navDrawer);

                //Set Data
                StorageReference PhotoProfil_Reference = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Photo_profil");
                PhotoProfil_Reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Photo_header = (ImageView) findViewById(id.photo_profil_Header);
                        Glide.with(getApplicationContext()).load(uri).into(Photo_header);
                    }
                });


                UserProfileReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            User userProfile = dataSnapshot.getValue(User.class);
                            TextView NomHeader = (TextView) findViewById(id.Nom_Prenom_Header);
                            NomHeader.setText(userProfile.getNom() + " " + userProfile.getPrenom());
                            Niveau = userProfile.getNiveau();
                            if ((userProfile.getLien_Projet() != null) && (userProfile.getLien_Projet() != "")) {
                                Etape = 3;
                            } else if ((userProfile.getLien_carte_etudiant() != null) && (userProfile.getLien_carte_etudiant() != "")) {
                                Etape = 2;
                            }
                        } catch (Exception e) {
                            Log.e("exception", e.toString());
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                toggle = new ActionBarDrawerToggle(this, drawerLayout, open, close);
                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                setupDrawerContent(navigationView);


                BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(id.bottom_navigation);
                bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                loadFragment(new Actualite_fragment());

            }else{
                startActivity(new Intent(getApplicationContext(), SignUp_Activity.class));
                finish();

            }

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
                        case id.News_menu_item:
                            fragment= new Actualite_fragment();
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
        Boolean logout = false;
        Class fragmentClass = Actualite_fragment.class;
        switch (item.getItemId()){
            case id.Logout_menu_item:
                try{
                    FirebaseAuth.getInstance().signOut();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                }
                logout = true;
                startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
                finish();
            case id.Profil_menu_item:
                fragmentClass = Profil_fragment.class;
                break;
            case id.CV_menu_item:
                fragmentClass = Resume_fragment.class;
                break;
            case id.Dossier_menu_item:
                fragmentClass = Verification_id_fragment.class;
                if(Niveau == 5) {
                    switch (Etape){
                        case 1:
                            fragmentClass = Verification_id_fragment.class;
                            break;
                        case 2:
                            fragmentClass = Import_Documents_fragment.class;
                            break;
                        case 3:
                            fragmentClass = Suivie_etape_physique_fragment.class;
                            break;
                    }
                }else{
                    AlertDialog.Builder PopUp = new AlertDialog.Builder(Home_Activity.this);
                    PopUp.setTitle("Vous n'êtes pas concernés");
                    PopUp.setMessage("Cette section ne concerne que les etudiants en fin de formation (5ème Année).");
                    PopUp.show();
                    fragmentClass = Actualite_fragment.class;
                }

                break;

            case id.rate_menu_item:
                fragmentClass = Rating_fragment.class;
                break;

            default:
                break;


        }
        try{
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!logout){
            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(id.fragmentLay, fragment);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();
            item.setChecked(true);
            setTitle(item.getTitle());
            drawerLayout.closeDrawers();
        }

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