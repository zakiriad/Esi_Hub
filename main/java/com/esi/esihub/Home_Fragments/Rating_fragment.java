package com.esi.esihub.Home_Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Rating_fragment extends Fragment {

    private Button Annuler, Ajouter;
    private TextView NbrChar;
    private EditText Comment;
    private RatingBar rating;
    private DatabaseReference ratingsReference = FirebaseDatabase.getInstance().getReference("Evaluations");

    public Rating_fragment() {}





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart(){
        super.onStart();
        Annuler = getActivity().findViewById(R.id.Annuler_Button_rating);
        Ajouter = getActivity().findViewById(R.id.Ajouter_Button_rating);
        Comment = getActivity().findViewById(R.id.Comment_Edittext_rating);
        NbrChar = getActivity().findViewById(R.id.NbrCaracteres_Text_rating);
        rating = getActivity().findViewById(R.id.ratingBar);
        rating.setNumStars(5);
        try {
            Comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    NbrChar.setText(String.valueOf(Comment.getText().toString().length())+"/500");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    NbrChar.setText(String.valueOf(Comment.getText().toString().length())+"/500");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    NbrChar.setText(String.valueOf(Comment.getText().toString().length())+"/500");
                }
            });




            Ajouter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference RatingRef = ratingsReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    RatingRef.child("Rating").setValue(rating.getRating());
                    if(Comment.getText().toString().length() <= 500){
                        RatingRef.child("Comment").setValue(Comment.getText().toString());
                        AlertDialog.Builder popUp = new AlertDialog.Builder(getActivity());
                        popUp.setTitle("Merci");
                        popUp.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        popUp.show();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentLay, new Actualite_fragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else{
                        AlertDialog.Builder popUp = new AlertDialog.Builder(getActivity());
                        popUp.setTitle("Erreur");
                        popUp.setMessage("Commentaire trop long");
                        popUp.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        popUp.show();
                        return;
                    }

                }
            });

            Annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentLay, new Actualite_fragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }
}