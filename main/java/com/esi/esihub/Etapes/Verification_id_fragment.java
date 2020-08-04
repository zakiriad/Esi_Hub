package com.esi.esihub.Etapes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.esi.esihub.Home_Activity;
import com.esi.esihub.Home_Fragments.Actualite_fragment;
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

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Verification_id_fragment extends Fragment {
    private Button Choisir, Confirmer, Annuler;
    private EditText Numero_id;
    private TextView NomFichier;
    private  DatabaseReference UserReference;
    private Uri filePath;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage;


    public Verification_id_fragment() {}



    @Override
    public void onStart(){
        super.onStart();
        Choisir = getActivity().findViewById(R.id.Choisir_Button_veri_id);
        Confirmer = getActivity().findViewById(R.id.Confirmer_Button_veri_id);
        Annuler = getActivity().findViewById(R.id.Annuler_Button_veri_id);
        Numero_id = getActivity().findViewById(R.id.Numero_etudiant_veri_id);
        NomFichier = getActivity().findViewById(R.id.Carte_etud_veri_id);
        UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        Choisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });
        Confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Numero_id.getText().toString())){
                    Numero_id.setError("Champs requis");
                }
                if(filePath != null){
                    AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Confirmation");
                    PopUp.setMessage("Confirmez-vous votre identité ?");
                    PopUp.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserReference.child("Numero_Carte_Etudiant").setValue(Numero_id.getText().toString());
                            uploadFile(filePath, "Carte_Etudiant");
                        }
                    });
                    PopUp.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.show();
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
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPdf();
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

                        UserReference.child(currentUser.getUid()).child(fileName).setValue(url);
                        Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),Import_Documents_fragment.class);
                        startActivity(intent);
                        getActivity().finish();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            filePath = data.getData();
            NomFichier.setText(data.getData().getLastPathSegment().toString());
        }else{
            Toast.makeText(getContext(), "Select a file", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_verification_id, container, false);
    }
}