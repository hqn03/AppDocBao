package com.example.appdocbao;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<TinTuc> {
    Context context;
    int resource;
    ArrayList<TinTuc> tinTucArrayList;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TinTuc> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.tinTucArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);

        TinTuc tinTuc = tinTucArrayList.get(position);
        ImageView img = convertView.findViewById(R.id.item_iv);
        //Chuyen url sang image
        Glide.with(convertView).load(tinTuc.getAnh()).into(img);

        TextView textView = convertView.findViewById(R.id.item_tv);
        textView.setText(tinTuc.getTieuDe());
        return convertView;
    }
}
