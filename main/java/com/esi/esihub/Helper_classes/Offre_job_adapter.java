package com.esi.esihub.Helper_classes;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esi.esihub.R;

import java.util.ArrayList;

public class Offre_job_adapter extends BaseAdapter {
    Context context;
    ArrayList<offre_job> arrayList;

    public Offre_job_adapter(Context context, ArrayList<offre_job> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Offre_job_adapter() {
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.offre_jobs_model, parent, false);

        TextView Nom = convertView.findViewById(R.id.Nom_offre_Job_model);
        TextView Societe = convertView.findViewById(R.id.Societe_offre_Job_model);
        TextView Email = convertView.findViewById(R.id.Email_offre_Job_model);
        ImageView logo = convertView.findViewById(R.id.Logo_offre_Job_model);

        Nom.setText(arrayList.get(position).getNom());
        Societe.setText(arrayList.get(position).getSociete());
        Email.setText(arrayList.get(position).getEmail());

        if((arrayList.get(position).getLien_logo() != null)&&(!TextUtils.isEmpty(arrayList.get(position).getLien_logo()) )){
            logo.setScaleX(1);
            logo.setScaleY(1);
            Glide.with(context).load(arrayList.get(position).getLien_logo()).into(logo);
        }

        return convertView;
    }
}
