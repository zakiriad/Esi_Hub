package com.esi.esihub.CV_Details;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esi.esihub.Helper_classes.Formation;
import com.esi.esihub.Home_Activity;
import com.esi.esihub.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class Ajout_formation_fragment extends Fragment {
    private Uri filePath;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage;
    private EditText Nom, Etablissement, Domaine,DateFin, DateDebut;
    Button Choisir, Annuler, Ajouter, Consulter;
    TextView NomFichier;


    final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    public Ajout_formation_fragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();


        Nom = getActivity().findViewById(R.id.Nom_Edittext_Ajout_formation);
        Etablissement= getActivity().findViewById(R.id.etablissement_Edittext_Ajout_formation);
        Domaine= getActivity().findViewById(R.id.Domaine_Edittext_Ajout_formation);
        DateFin = getActivity().findViewById(R.id.DateFin_Edittext_Ajout_formation);
        DateDebut = getActivity().findViewById(R.id.DateDebut_Edittext_Ajout_formation);
        Choisir= getActivity().findViewById(R.id.select_Button_Ajout_formation);
        Annuler = getActivity().findViewById(R.id.Annuler_Button_Ajout_formation);
        Ajouter = getActivity().findViewById(R.id.Ajouter_Button_Ajout_formation);
        Consulter= getActivity().findViewById(R.id.VoirFormation_Button_Ajout_formation);
        NomFichier= getActivity().findViewById(R.id.fichier_Text_Ajout_formation);



        Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nom.setText("");
                Etablissement.setText("");
                Domaine.setText("");
            }
        });


        Consulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLay, new Visualiser_formations());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        Choisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    //File Selecter
                    selectPdf();
                }else{
                    try{
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }
        });
        Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Domaine.getText().toString())){
                    Domaine.setError("Champs requis");
                    return;
                }
                if(TextUtils.isEmpty(Nom.getText().toString())){
                    Nom.setError("Champs requis");
                    return;
                }
                if(TextUtils.isEmpty(Etablissement.getText().toString())){
                    Etablissement.setError("Champs requis");
                    return;
                }
                if(TextUtils.isEmpty(DateFin.getText().toString())){
                    DateFin.setError("Champs requis");
                    return;
                }
                if(TextUtils.isEmpty(DateDebut.getText().toString())){
                    DateDebut.setError("Champs requis");
                    return;
                }
                if(filePath == null){
                    NomFichier.setError("selectionner un fichier");
                    return;
                }


                uploadFile(filePath);
            }
        });









    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "please provide a permission", Toast.LENGTH_SHORT).show();

        }
    }
    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 86 && resultCode == getActivity().RESULT_OK && data != null) {
            filePath = data.getData();
            NomFichier.setText(data.getData().getLastPathSegment().toString());
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Select a file", Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadFile(Uri filePath) {

        // montre l'evolution de l'upload
        progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final String fileName = Nom.getText().toString();
        StorageReference storageReference = storage.getReference();
        storageReference.child(currentUser.getUid()).child(fileName).putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Formation formation = new Formation(Nom.getText().toString(), Domaine.getText().toString(),Etablissement.getText().toString(),url,DateFin.getText().toString(), DateDebut.getText().toString());
                        ResumeReference.child("Formations").child(formation.getNom()).setValue(formation);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Task fails", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        // Creating a progress tracker
                        int currentProgress = (int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                        if(currentProgress == 100){
                            progressDialog.dismiss();
                        }
                    }
                });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_ajout_formation_fragment, container, false);
    }
}