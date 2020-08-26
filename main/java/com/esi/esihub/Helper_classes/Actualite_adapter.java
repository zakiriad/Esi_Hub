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
import java.util.zip.Inflater;

public class Actualite_adapter  extends BaseAdapter {
    Context context;
    ArrayList<Actualite> arrayList;

    public Actualite_adapter(Context context, ArrayList<Actualite> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Actualite_adapter() {
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
        convertView  = LayoutInflater.from(context).inflate(R.layout.actualite_model, parent, false);

        TextView Titre = convertView.findViewById(R.id.Titre_actualite_model);
        TextView SousTitre = convertView.findViewById(R.id.SousTitre_actualite_model);
        TextView lien = convertView.findViewById(R.id.lien_actualite_model);

        ImageView photo = convertView.findViewById(R.id.Photo_actualite_model);

        Titre.setText(arrayList.get(position).getTitre());
        SousTitre.setText(arrayList.get(position).getSousTitre());
        lien.setText(arrayList.get(position).getLien());

        if((arrayList.get(position).getLien_photo() != null)&&(!TextUtils.isEmpty(arrayList.get(position).getLien_photo()))){
            photo.setScaleX(1);
            photo.setScaleY(1);
            Glide.with(context).load(arrayList.get(position).getLien_photo()).into(photo);
        }
        return convertView;
    }
}
