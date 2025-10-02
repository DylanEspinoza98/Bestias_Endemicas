package com.example.bestiasendemicas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bestiasendemicas.AddEditAnimal.java;
import com.example.bestiasendemicas.R;
import com.example.bestiasendemicas.model.Animal;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private Context context;
    private List<Animal> animales;
    private OnAnimalActionListener listener;

    public interface OnAnimalActionListener {
        void onEditarAnimal(Animal animal);
        void onEliminarAnimal(Animal animal);
        void onVerDetalles(Animal animal);
    }

    public AnimalAdapter(Context context, List<Animal> animales, OnAnimalActionListener listener) {
        this.context = context;
        this.animales = animales;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal_crud, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animales.get(position);
        holder.bind(animal);
    }

    @Override
    public int getItemCount() {
        return animales.size();
    }

    public void actualizarAnimales(List<Animal> nuevosAnimales) {
        this.animales = nuevosAnimales;
        notifyDataSetChanged();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre, tvDescripcion, tvRegion;
        private ImageView ivFoto, ivFavorito;
        private Button btnEditar, btnEliminar, btnVerMas;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tv_nombre_animal);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion_animal);
            tvRegion = itemView.findViewById(R.id.tv_region_animal);
            ivFoto = itemView.findViewById(R.id.iv_foto_animal);
            ivFavorito = itemView.findViewById(R.id.iv_favorito_animal);
            btnEditar = itemView.findViewById(R.id.btn_editar_animal);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_animal);
            btnVerMas = itemView.findViewById(R.id.btn_ver_mas_animal);
        }

        public void bind(Animal animal) {
            tvNombre.setText(animal.getNombre());
            tvDescripcion.setText(animal.getDescripcion());

            // Mostrar nombre de región (esto requeriría una consulta adicional o join)
            tvRegion.setText("Región ID: " + animal.getRegionId());

            // Mostrar ícono de favorito
            if (animal.isEsFavorito()) {
                ivFavorito.setVisibility(View.VISIBLE);
            } else {
                ivFavorito.setVisibility(View.GONE);
            }

            // TODO: Cargar imagen desde URL usando Picasso o Glide
            // Por ahora usar imagen por defecto
            ivFoto.setImageResource(R.drawable.ic_animal_placeholder);

            // Configurar listeners de botones
            btnEditar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditarAnimal(animal);
                }
            });

            btnEliminar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEliminarAnimal(animal);
                }
            });

            btnVerMas.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVerDetalles(animal);
                }
            });
        }
    }
}