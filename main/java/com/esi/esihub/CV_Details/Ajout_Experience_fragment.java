package com.esi.esihub.CV_Details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.esi.esihub.Helper_classes.Experience;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Ajout_Experience_fragment extends Fragment {
    private EditText Etablissement = getActivity().findViewById(R.id.etablissement_Edittext_Ajout_experience),
            DateDebut = getActivity().findViewById(R.id.DateDebut_Edittext_Ajout_experience),
            DateFin = getActivity().findViewById(R.id.DateFin_Edittext_Ajout_experience);
    private Spinner Nom = getActivity().findViewById(R.id.Nom_Spinner_Ajout_experience);
    private Button Annuler = getActivity().findViewById(R.id.Annuler_Button_Ajout_experience),
            Ajouter = getActivity().findViewById(R.id.Ajouter_Button_Ajout_experience),
            Consulter = getActivity().findViewById(R.id.Consulter_Button_Ajout_experience);

    final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    public Ajout_Experience_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart(){
        super.onStart();


        Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLay, new Ajout_Experience_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(DateDebut.getText().toString())){
                    DateDebut.setError("Indiquer une date de debut");
                }
                if(TextUtils.isEmpty(DateFin.getText().toString())){
                    DateFin.setError("Indiquer une date de fin");
                }
                if(TextUtils.isEmpty(Etablissement.getText().toString())){
                    Etablissement.setError("Indiquer le nom de l'etablissement");
                }
                Experience experience = new Experience(Nom.getSelectedItem().toString(), Etablissement.getText().toString(), DateDebut.getText().toString(),DateFin.getText().toString());
                ResumeReference.child("Experiences").child(experience.getEtablissment()).setValue(experience);
            }
        });
        Consulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLay, new Resume_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ajout__experience_fragment, container, false);
    }
}