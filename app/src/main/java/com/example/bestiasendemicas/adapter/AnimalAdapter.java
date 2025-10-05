package com.example.bestiasendemicas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bestiasendemicas.AddEditAnimal;
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_animal, parent, false);
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

        private TextView tvNombre, tvDescripcion, tvFavorito;
        private ImageView ivFoto;
        private Button btnEditar, btnEliminar, btnVerMas;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tv_nombre_animal);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion_animal);
            tvFavorito = itemView.findViewById(R.id.tv_favorito_indicator);
            ivFoto = itemView.findViewById(R.id.iv_foto_animal);
            btnEditar = itemView.findViewById(R.id.btn_editar_animal);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_animal);
            btnVerMas = itemView.findViewById(R.id.btn_ver_mas_animal);
        }

        public void bind(Animal animal) {
            tvNombre.setText(animal.getNombre());
            tvDescripcion.setText(animal.getDescripcion());

            //Mostrar indicador de favorito
            if (animal.isEsFavorito()) {
                tvFavorito.setVisibility(View.VISIBLE);
            } else {
                tvFavorito.setVisibility(View.GONE);
            }

            //Cargar solo desde galeria
            cargarImagenDeGaleria(animal.getRutaImagen(), ivFoto);

            //Listeners para botones
            btnEditar.setOnClickListener(v -> listener.onEditarAnimal(animal));
            btnEliminar.setOnClickListener(v -> listener.onEliminarAnimal(animal));
            btnVerMas.setOnClickListener(v -> listener.onVerDetalles(animal));
        }

        private void cargarImagenDeGaleria(String rutaImagen, ImageView imageView) {
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                try {
                    //Solo cargar desde URI de galería
                    Uri uri = Uri.parse(rutaImagen);
                    imageView.setImageURI(uri);
                } catch (Exception e) {
                    //Si falla, mostrar placeholder
                    imageView.setImageResource(R.drawable.ic_animal_placeholder);
                }
            } else {
                //Sin imagen, mostrar placeholder
                imageView.setImageResource(R.drawable.ic_animal_placeholder);
            }
        }
    }



}