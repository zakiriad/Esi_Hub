package com.esi.esihub.CV_Details;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.widget.PopupMenu;
import com.esi.esihub.R;


public class Resume_fragment extends Fragment {
    private TextView apropos;
    private LinearLayout Contact, Skills, Langue, Education, Experience;


    public Resume_fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public  void onStart(){
        super.onStart();
        apropos = getActivity().findViewById(R.id.Apropos_section_resume);
        Contact = getActivity().findViewById(R.id.Contact_section_resume);
        Skills = getActivity().findViewById(R.id.Skills_section_resume);
        Langue = getActivity().findViewById(R.id.Langues_section_resume);
        Education = getActivity().findViewById(R.id.Education_section_resume);
        Experience = getActivity().findViewById(R.id.Experience_section_resume);


        try {
            apropos.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Que Voulez vous faire");
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Apropos_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    PopUp.show();




                    return true;
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resume_fragment, container, false);
    }
}