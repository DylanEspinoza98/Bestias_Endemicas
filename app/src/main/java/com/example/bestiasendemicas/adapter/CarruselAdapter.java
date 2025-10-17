package com.example.bestiasendemicas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestiasendemicas.CarruselItem;
import com.example.bestiasendemicas.R;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class CarruselAdapter extends RecyclerView.Adapter<CarruselAdapter.ViewHolder> {

    // Permite que cualquier actividad o fragment que use el adaptador reciba eventos de click y evita acoplar el adaptador a un Context específico.
    public interface OnItemClickListener {
        void onItemClick(CarruselItem item);
    }
    private final List<CarruselItem> items;
    private final OnItemClickListener listener;

    //Recibe la lista de items y el listener sin depender del contexto
    public CarruselAdapter(List<CarruselItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usamos el contexto del ViewGroup padre, que es más seguro.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrusel_zone, parent, false);
        return new ViewHolder(view);
    }

    /** Usa el operador módulo (%) para que la lista se repita infinitamente.*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CarruselItem item = items.get(position % items.size()); // Con esto, la lista se repite

        holder.imageView.setImageResource(item.getImageResource());
        holder.zoneName.setText(item.getZoneName());

        holder.cardView.setOnClickListener(v -> {
            listener.onItemClick(item);
        });
    }
    /** Se combina con el metodo de arriba para que la lista nunca termine, creando el efecto de carrusel infinito.*/
    @Override
    public int getItemCount() {
        // >>> CAMBIO CLAVE: DEVUELVE UN NÚMERO MUY GRANDE <<<
        // Esto hace que el ViewPager2 crea que la lista es "infinita".
        return Integer.MAX_VALUE;
    }

    // El ViewHolder no necesita cambios
    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageView;
        TextView zoneName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_zone);
            imageView = itemView.findViewById(R.id.image_view_zone);
            zoneName = itemView.findViewById(R.id.text_view_zone_name);
        }
    }
}
