package com.esi.esihub.Home_Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esi.esihub.Helper_classes.Offre_formation;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class Formation_fragment extends Fragment {



    public Formation_fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onStart(){
        super.onStart();
        final LinearLayout MainLayout = (LinearLayout)getActivity().findViewById(R.id.Formation_mainLayout);

        final DatabaseReference Offres_Reference = FirebaseDatabase.getInstance().getReference("Offres").child("Formations");
        Offres_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        Offre_formation offre = postSnapshot.getValue(Offre_formation.class);

                        LinearLayout NewOffer = new LinearLayout(getActivity());
                        NewOffer.setOrientation(LinearLayout.HORIZONTAL);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(offre.getNom());

                        TextView Domaine = new TextView(getActivity());
                        Domaine.setText(offre.getDomaine());

                        NewOffer.addView(Nom);
                        NewOffer.addView(Domaine);
                        MainLayout.addView(NewOffer);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_formation_fragment, container, false);
    }
}