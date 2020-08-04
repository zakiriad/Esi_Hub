package com.esi.esihub.CV_Details;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        apropos = getActivity().findViewById(R.id.Apropos_section_resume);
        NbrChar = getActivity().findViewById(R.id.NbrCaracteres_Text_apropos);
        Ajouter = getActivity().findViewById(R.id.Ajouter_Button_Ajout_apropos);
        Annuler = getActivity().findViewById(R.id.Annuler_Button_Ajout_apropos);
        ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        try {
                apropos.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    NbrChar.setText(String.valueOf(apropos.getText().toString().length()) +"/500");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    NbrChar.setText(String.valueOf(apropos.getText().toString().length()) +"/500");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    NbrChar.setText(String.valueOf(apropos.getText().toString().length()) +"/500");
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
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("A propos_Cv", apropos.getText().toString());
                    editor.commit();

                    ResumeReference.child("Apropos").setValue(apropos.getText().toString());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentLay, new Resume_fragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
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