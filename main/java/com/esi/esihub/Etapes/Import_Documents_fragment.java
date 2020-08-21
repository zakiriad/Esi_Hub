package com.esi.esihub.Etapes;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esi.esihub.CV_Details.Resume_fragment;
import com.esi.esihub.Helper_classes.User;
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

public class Import_Documents_fragment extends Fragment {
    private EditText NumeroProjet;
    private TextView NomFichier_memoire, NomFichier_projet;
    private Button Choisir_Memoire, Choisir_Projet, Annuler, Confirmer;
    private CheckBox Validation;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri filePath_memoire, filePath_projet;
    private DatabaseReference UserReference;

    public Import_Documents_fragment() {}




    @Override
    public void onStart(){
        super.onStart();
        NumeroProjet = getActivity().findViewById(R.id.NumeroProjet_import_documents);
        NomFichier_memoire = getActivity().findViewById(R.id.Fichier_Memoire_Import_documents);
        Choisir_Memoire = getActivity().findViewById(R.id.Choisir_Memoiree_Button_Import_documents);
        NomFichier_projet = getActivity().findViewById(R.id.Fichier_Projet_Import_documents);
        Choisir_Projet = getActivity().findViewById(R.id.Choisir_Projet_Button_Import_documents);
        Annuler = getActivity().findViewById(R.id.Annuler_Button_Import_documents);
        Confirmer = getActivity().findViewById(R.id.Confirmer_Button_Import_documents);
        Validation = getActivity().findViewById(R.id.Valider_checkbox_import_document);
        UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLay, new Resume_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Choisir_Projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    //File Selecter
                    selectZip();
                }else{
                    try{
                        ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



            }
        });
        Choisir_Memoire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    //File Selecter
                    selectPdf();
                }else{
                    try{
                        ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });
        Confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(NumeroProjet.getText().toString())){
                    NumeroProjet.setError("Veuillez indiquer votre numero de projet");
                }
                if(!Validation.isChecked()){
                  Validation.setError("Vous devez cocher cette case");
                }
                if((filePath_memoire != null)&&(filePath_projet != null)){
                    final AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Attention");
                    PopUp.setMessage("Une fois cette etape valider vous ne pouvez plus modifier vos documents");
                    PopUp.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserReference.child("Numero_Projet").setValue(NumeroProjet.getText().toString());
                            UserReference.child("confirmer_Dossier").setValue(true);
                            uploadFile(filePath_memoire,"Memoire");
                            uploadFile(filePath_projet, "Projet");
                        }
                    });
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.show();
                }

            }
        });

    }




    private void uploadFile(Uri filePath, final String fileName) {

        // Show
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference storageReference = storage.getReference();

        storageReference.child(currentUser.getUid()).child(fileName).putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        //To store the download url
                        if(fileName == "Memoire"){
                            UserReference.child("lien_Memoire").setValue(url);

                        }else{
                            UserReference.child("lien_Projet").setValue(url);


                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Suivie_etape_physique_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();


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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else{
            Toast.makeText(getContext(), "please provide a permission", Toast.LENGTH_SHORT).show();
        }
        if(requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectZip();
        }else{
            Toast.makeText(getContext(), "please provide a permission", Toast.LENGTH_SHORT).show();
        }
    }
    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    private void selectZip() {
        Intent intent = new Intent();
        intent.setType("application/zip");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            filePath_memoire = data.getData();
            NomFichier_memoire.setText(data.getData().getLastPathSegment().toString());
        }else{
            Toast.makeText(getContext(), "Select a file", Toast.LENGTH_SHORT).show();
        }
        if(requestCode == 2 && resultCode == getActivity().RESULT_OK && data != null) {
            filePath_projet = data.getData();
            NomFichier_projet.setText(data.getData().getLastPathSegment().toString());
        }else{
            Toast.makeText(getContext(), "Select a file", Toast.LENGTH_SHORT).show();
        }

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_import__documents, container, false);
    }
}