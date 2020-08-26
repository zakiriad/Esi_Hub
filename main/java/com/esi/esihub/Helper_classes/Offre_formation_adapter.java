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

public class Offre_formation_adapter  extends BaseAdapter {
    Context context;
    ArrayList<Offre_formation> arrayList;


    public Offre_formation_adapter(Context context, ArrayList<Offre_formation> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    public Offre_formation_adapter() {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.offre_formations_model, parent, false);

        TextView Nom = convertView.findViewById(R.id.Nom_offre_Formation_model);
        TextView Etablissement = convertView.findViewById(R.id.Etablissement_offre_Formation_model);
        TextView DateDebut = convertView.findViewById(R.id.DateDebut_offre_Formation_model);
        ImageView logo = convertView.findViewById(R.id.Logo_offre_Formation_model);

        Nom.setText(arrayList.get(position).getNom());
        Etablissement.setText(arrayList.get(position).getEtablissement());
        DateDebut.setText(arrayList.get(position).getDateDebut());

        if((arrayList.get(position).getLien_Image() != null)&&(!TextUtils.isEmpty(arrayList.get(position).getLien_Image()))){
            logo.setScaleX(1);
            logo.setScaleY(1);
            Glide.with(context).load(arrayList.get(position).getLien_Image()).into(logo);
        }



        return convertView;
    }
}
