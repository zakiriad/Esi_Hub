package com.esi.esihub.Home_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.esi.esihub.Helper_classes.Form_adapter;
import com.esi.esihub.Helper_classes.Formation;
import com.esi.esihub.Helper_classes.Formulaire;
import com.esi.esihub.R;

import java.util.ArrayList;
import java.util.List;

public class Formulaire_fragment extends Fragment {


    public Formulaire_fragment() { }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart(){
        super.onStart();
        //on va creer des formulaires :
        try {
            List<Formulaire> forms = new ArrayList<>();
            final ListView listView = (ListView) getActivity().findViewById(R.id.Formulaire_mainLayout);

            Formulaire form1 = new Formulaire("Quelle est votre situation actuelle", "je suis au chomage", "j'ai continué mes etudes", "j'ai un travail");
            Formulaire form2 = new Formulaire("Quelle est votre domaine", "Développeur Web", "Réseau", "DevOps");
            Formulaire form3 = new Formulaire("comment pouvez vous aider l'ecole", "Proposer des formations", "Encadrer des etudiants de l'ecole", "Etablir un partenariat avec l'ecole");

            forms.add(form1);
            forms.add(form2);
            forms.add(form3);
            final Form_adapter adapter = new Form_adapter(getContext(), (ArrayList<Formulaire>) forms);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulaire, container, false);
    }
}