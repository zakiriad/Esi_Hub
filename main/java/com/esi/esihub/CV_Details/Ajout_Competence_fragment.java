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
import android.widget.TextView;

import com.esi.esihub.Helper_classes.Competence;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Ajout_Competence_fragment extends Fragment {
    private EditText Nom = getActivity().findViewById(R.id.Nom_Edittext_Ajout_competence);
    private Spinner Niveau = getActivity().findViewById(R.id.Niveau_Spinner_Ajout_competence);
    private Button Annuler = getActivity().findViewById(R.id.Annuler_Button_Ajout_competence),
            Ajouter = getActivity().findViewById(R.id.Ajouter_Button_Ajout_competence),
            Consulter = getActivity().findViewById(R.id.Consulter_Button_Ajout_competence);

    final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    public Ajout_Competence_fragment() {
        // Required empty public constructor
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
                Nom.setText("");
            }
        });
        Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Nom.getText().toString())){
                    Nom.setError("Indiquer le nom de la compétence");
                }
                Competence competence = new Competence(Nom.getText().toString(), Niveau.getSelectedItem().toString());
                ResumeReference.child("Competences").child(Nom.getText().toString()).setValue(competence);

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
        return inflater.inflate(R.layout.fragment_ajout__competence_fragment, container, false);
    }
}