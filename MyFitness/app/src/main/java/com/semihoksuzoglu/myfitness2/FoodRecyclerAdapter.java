package com.semihoksuzoglu.myfitness2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodHolder> {

    private ArrayList<String> yemekListesi;
    private ArrayList<String> yemekKal;
    SharedPreferences sharedPreferences;
    KcalFragment fragment;

    public FoodRecyclerAdapter(ArrayList<String> yemekListesi, ArrayList<String> yemekKal, Context context,KcalFragment fragment) {
        this.yemekListesi = yemekListesi;
        this.yemekKal = yemekKal;
        this.fragment = fragment;
        sharedPreferences = context.getSharedPreferences("Shared",0);
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater  layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_eklenmisyemekler,parent,false);

        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodHolder holder, final int position) {
        holder.YemekIsmi.setText(yemekListesi.get(position));
        holder.YemekKal.setText(yemekKal.get(position));
        holder.actionButton.setVisibility(View.INVISIBLE);
        /*
        boolean newFood = sharedPreferences.getBoolean("NewFood",true);
        if(!newFood){
            holder.actionButton.setVisibility(View.INVISIBLE);
        }
         */
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.YemekListesi.remove(position);
                fragment.YemekCalDegeri.remove(position);
                fragment.foodRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return yemekListesi.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {

        TextView YemekIsmi;
        TextView YemekKal;
        FloatingActionButton actionButton;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);

            YemekIsmi = itemView.findViewById(R.id.YemekIsmi);
            YemekKal = itemView.findViewById(R.id.YemekKal);
            actionButton = itemView.findViewById(R.id.AddedFoodRemove);
        }
    }
}
