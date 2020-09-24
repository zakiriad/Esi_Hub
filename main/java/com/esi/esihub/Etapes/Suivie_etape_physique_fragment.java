package com.esi.esihub.Etapes;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esi.esihub.Helper_classes.Verification_Dossier;
import com.esi.esihub.Home_Fragments.Actualite_fragment;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Suivie_etape_physique_fragment extends Fragment {
    private TextView identite, documents, livres;
    private ProgressBar progressBar;
    private Button retour;
    private DatabaseReference VerificationReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Verifications");

    public Suivie_etape_physique_fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart(){
        super.onStart();
        identite = getActivity().findViewById(R.id.id_text_suivie_etapes);
        documents = getActivity().findViewById(R.id.Documents_text_suivie_etapes);
        livres = getActivity().findViewById(R.id.prets_text_suivie_etapes);
        progressBar = getActivity().findViewById(R.id.progress_bar_suivie_etapes);
        retour = getActivity().findViewById(R.id.Retour_Button_suivie_etapes);

        VerificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Verification_Dossier verificationDossier = dataSnapshot.getValue(Verification_Dossier.class);
                    Boolean identite_db = verificationDossier.getId();
                    Boolean documents_db = verificationDossier.getDocuments();
                    Boolean livres_db = verificationDossier.getLivres();

                    if(identite_db != null){
                        if(identite_db){
                            String identite_txt= "Verification d'identité";
                            identite.setPaintFlags(identite.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                            identite.setText(identite_txt);


                            progressBar.setProgress(33);
                        }else{
                            identite.setTextColor(Integer.parseInt("#FF0000"));
                        }
                    }
                    if(documents_db != null){
                        if(identite_db){
                            String document_txt= "Verification des documents";
                            documents.setPaintFlags(documents.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                            documents.setText(document_txt);



                            progressBar.setProgress(66);
                        }else{
                            documents.setTextColor(Integer.parseInt("#FF0000"));
                        }
                    }
                    if(livres_db != null){
                        if(livres_db){
                            String livres_txt= "Verification des prêts";
                            livres.setPaintFlags(livres.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                            livres.setText(livres_txt);




                            progressBar.setProgress(100);
                        }else{
                            livres.setTextColor(Integer.parseInt("#FF0000"));
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLay, new Actualite_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suivie_etape_physique, container, false);
    }
}