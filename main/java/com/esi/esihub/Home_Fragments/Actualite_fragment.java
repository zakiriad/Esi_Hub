package com.esi.esihub.Home_Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esi.esihub.Helper_classes.Actualite;
import com.esi.esihub.Helper_classes.Actualite_adapter;
import com.esi.esihub.R;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Actualite_fragment extends Fragment {
    

    public Actualite_fragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void onStart(){
        super.onStart();

        final DatabaseReference Actualites_Reference = FirebaseDatabase.getInstance().getReference("Actualites");

        Actualites_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    List<Actualite> news = new ArrayList<>();
                    final ListView listView = (ListView) getActivity().findViewById(R.id.Actualite_mainlayout);
                    for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                       final  Actualite actualite = postSnapshot.getValue(Actualite.class);
                       news.add(actualite);
                    }
                    final Actualite_adapter adapter = new Actualite_adapter(getContext(), (ArrayList<Actualite>) news);
                    listView.setAdapter(adapter);
                    //Implementer l'ouverture des liens externes.


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

        return inflater.inflate(R.layout.fragment_actualite_fragment, container, false);
    }
}