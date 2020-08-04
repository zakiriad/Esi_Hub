package com.esi.esihub.Home_Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esi.esihub.Helper_classes.offre_job;
import com.esi.esihub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Job_fragment extends Fragment {

    public Job_fragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();

        final LinearLayout MainLayout = (LinearLayout)getActivity().findViewById(R.id.Job_mainlayout);
        final DatabaseReference Offres_Reference = FirebaseDatabase.getInstance().getReference("Offres").child("Jobs");

        Offres_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        offre_job offre = postSnapshot.getValue(offre_job.class);

                        LinearLayout NewOffer = new LinearLayout(getActivity());
                        NewOffer.setOrientation(LinearLayout.HORIZONTAL);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(offre.getNom());

                        TextView Societe = new TextView(getActivity());
                        Societe.setText(offre.getSociete());

                        NewOffer.addView(Nom);
                        NewOffer.addView(Societe);

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_fragment, container, false);
    }
}