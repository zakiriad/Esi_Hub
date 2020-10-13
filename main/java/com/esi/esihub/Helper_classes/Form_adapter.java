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

public class Form_adapter extends BaseAdapter {
    Context context;
    ArrayList<Formulaire> arrayList;


    public Form_adapter(Context context, ArrayList<Formulaire> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Form_adapter() {
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
        convertView  = LayoutInflater.from(context).inflate(R.layout.form_model, parent, false);

        TextView Question = convertView.findViewById(R.id.question_form);
        TextView ans1 = convertView.findViewById(R.id.answer1_form);
        TextView ans2 = convertView.findViewById(R.id.answer2_form);
        TextView ans3 = convertView.findViewById(R.id.answer3_form);


        Question.setText(arrayList.get(position).getQuestion());
        ans1.setText(arrayList.get(position).getAnswer1());
        ans2.setText(arrayList.get(position).getAnswer2());
        ans3.setText(arrayList.get(position).getAnswer3());

        return convertView;
    }
}
