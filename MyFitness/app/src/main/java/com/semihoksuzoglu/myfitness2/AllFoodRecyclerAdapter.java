package com.semihoksuzoglu.myfitness2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllFoodRecyclerAdapter extends RecyclerView.Adapter<AllFoodRecyclerAdapter.AllFoodHolder> {

    ArrayList<String> TumYemeklerAdapter;
    ArrayList<String> TumYemekOlcekAdapter;
    ArrayList<String> TumYemekKalAdapter;
    KcalFragment kcalFragment;
    Context context;
    SharedPreferences sharedPreferences;

    public AllFoodRecyclerAdapter(ArrayList<String> tumYemeklerAdapter, ArrayList<String> tumYemekOlcekAdapter, ArrayList<String> tumYemekKalAdapter, KcalFragment fragment, Context context) {
        TumYemeklerAdapter = tumYemeklerAdapter;
        TumYemekOlcekAdapter = tumYemekOlcekAdapter;
        TumYemekKalAdapter = tumYemekKalAdapter;
        kcalFragment = fragment;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Shared",0);
    }

    @NonNull
    @Override
    public AllFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_tum_yemekler,parent,false);

        return new AllFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllFoodHolder holder, final int position) {
        holder.EklenecekYemekIsmı.setText(TumYemeklerAdapter.get(position));
        holder.EklenecekYemekOlcek.setText(TumYemekOlcekAdapter.get(position));
        holder.EklenecekYemekKalori.setText(""+TumYemekKalAdapter.get(position)+" Kcal");
        holder.AlladdFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kcalFragment.YemekListesi.add(TumYemeklerAdapter.get(position));
                kcalFragment.YemekCalDegeri.add(TumYemekKalAdapter.get(position));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NewFood",true);
                editor.commit();
                kcalFragment.foodRecyclerAdapter.notifyDataSetChanged();
                Toast.makeText(context,"Yemek Eklendi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return TumYemeklerAdapter.size();
    }

    class AllFoodHolder extends RecyclerView.ViewHolder{

        TextView EklenecekYemekIsmı;
        TextView EklenecekYemekOlcek;
        TextView EklenecekYemekKalori;
        FloatingActionButton AlladdFood;

        public AllFoodHolder(@NonNull View itemView) {
            super(itemView);
            EklenecekYemekIsmı = itemView.findViewById(R.id.FoodName);
            EklenecekYemekOlcek = itemView.findViewById(R.id.FoodPalt);
            EklenecekYemekKalori = itemView.findViewById(R.id.FoodCalValue);
            AlladdFood = itemView.findViewById(R.id.FoodAdd);
        }
    }

}
