package com.esi.esihub.Home_Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esi.esihub.Helper_classes.User;
import com.esi.esihub.Home_Activity;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profil_fragment extends Fragment {

    public Profil_fragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onStart(){
        super.onStart();


        ImageView photo_profil;
        final EditText Nom, Prenom, Esi_mail, other_mail, phone_number, birth_day, birth_place, wilaya;
        Button Edit_button = getActivity().findViewById(R.id.Edit_Button_profil),
                Cancel_button = getActivity().findViewById(R.id.Cancel_Button_profil);


        Nom = getActivity().findViewById(R.id.Nom_Edittext_profil);
        Prenom = getActivity().findViewById(R.id.Prenom_Edittext_profil);
        Esi_mail = getActivity().findViewById(R.id.Email_Edittext_profil);
        other_mail = getActivity().findViewById(R.id.OtherEmail_Edittext_profil);
        phone_number = getActivity().findViewById(R.id.Telephone_Edittext_profil);
        birth_day = getActivity().findViewById(R.id.BirthDay_Edittext_profil);
        birth_place = getActivity().findViewById(R.id.BirthPlace_Edittext_profil);
        wilaya = getActivity().findViewById(R.id.Wilaya_Edittext_profil);



        final DatabaseReference profilReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        profilReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    User user  = dataSnapshot.getValue(User.class);
                    Nom.setText(user.getNom());
                    Prenom.setText(user.getPrenom());
                    Esi_mail.setText(user.getEmail());
                    other_mail.setText(user.getOther_email());
                    phone_number.setText(user.getTelephone());
                    birth_day.setText(user.getDate_de_naissance());
                    birth_place.setText(user.getLieu_de_naissance());
                    wilaya.setText(user.getWilaya());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(Nom.getText().toString())){
                    Nom.setError("Ce champs ne doit pas etre vide");
                    return;
                }
                if(TextUtils.isEmpty(Prenom.getText().toString())){
                    Prenom.setError("Ce champs ne doit pas etre vide");
                    return;
                }
                if(TextUtils.isEmpty(Esi_mail.getText().toString())){
                    Esi_mail.setError("Ce champs ne doit pas etre vide");
                    return;
                }
                User user = new User(Nom.getText().toString(), Prenom.getText().toString(), birth_day.getText().toString(), birth_place.getText().toString(), Esi_mail.getText().toString(), other_mail.getText().toString(),phone_number.getText().toString(),wilaya.getText().toString());
                try{
                    profilReference.setValue(user);
                }catch(Exception e){
                    e.printStackTrace();
                }


                //TODO:
                /*
                * UpDate Data
                *
                *
                * */
            }
        });


        Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), Home_Activity.class));
                getActivity().finish();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_fragment, container, false);
    }
}