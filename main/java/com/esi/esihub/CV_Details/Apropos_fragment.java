package com.esi.esihub.CV_Details;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esi.esihub.Helper_classes.Resume;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Apropos_fragment extends Fragment {

    private EditText apropos;
    private TextView NbrChar;
    private Button Ajouter, Annuler;
    private DatabaseReference ResumeReference;

    public Apropos_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart(){
        super.onStart();
        apropos = getActivity().findViewById(R.id.Apropos_Edittext_apropos);
        NbrChar = getActivity().findViewById(R.id.NbrCaracteres_Text_apropos);
        Ajouter = getActivity().findViewById(R.id.Ajouter_Button_Ajout_apropos);
        Annuler = getActivity().findViewById(R.id.Annuler_Button_Ajout_apropos);
        ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        try {
                ResumeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Resume userResume = dataSnapshot.getValue(Resume.class);
                        apropos.setText(userResume.getApropos());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                apropos.addTextChangedListener(new TextWatcher() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    NbrChar.setText(String.valueOf(apropos.getText().toString().length()) +"/500");
                    if(apropos.getText().toString().length() > 500) NbrChar.setTextColor(getActivity().getColor(R.color.Red));
                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    NbrChar.setText(String.valueOf(apropos.getText().toString().length()) +"/500");
                    if(apropos.getText().toString().length() > 500) NbrChar.setTextColor(getActivity().getColor(R.color.Red));

                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void afterTextChanged(Editable s) {
                    NbrChar.setText(String.valueOf(apropos.getText().toString().length()) +"/500");
                    if(apropos.getText().toString().length() > 500) NbrChar.setTextColor(getActivity().getColor(R.color.Red));

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the value to SharedPreference to use it later
                try{
                    if(apropos.getText().toString().length() > 500) {
                        ResumeReference.child("apropos").setValue(apropos.getText().toString());
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentLay, new Resume_fragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }else{
                        Toast.makeText(getContext(), "Text trop long", Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        Annuler.setOnClickListener(new View.OnClickListener() {
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
        return inflater.inflate(R.layout.fragment_apropos_fragment, container, false);
    }
}