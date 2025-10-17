package com.example.bestiasendemicas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestiasendemicas.R;
import com.example.bestiasendemicas.model.Animal;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private Context context; // Contexto de la app para inflar layouts
    private List<Animal> animales; // Lista de animales a mostrar
    private OnAnimalActionListener listener; // Interfaz para manejar acciones de botones

    public interface OnAnimalActionListener {
        void onEditarAnimal(Animal animal); // Callback cuando se presiona el botón editar
        void onEliminarAnimal(Animal animal); // Callback cuando se presiona el botón eliminar
        void onVerDetalles(Animal animal); // Callback cuando se presiona el botón ver más
    }

    public AnimalAdapter(Context context, List<Animal> animales, OnAnimalActionListener listener) {
        this.context = context; // Inicializa el contexto
        this.animales = animales; // Inicializa la lista de animales
        this.listener = listener; // Inicializa el listener
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout de un item y crea un ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        // Vincula los datos del animal al ViewHolder
        Animal animal = animales.get(position);
        holder.bind(animal);
    }

    @Override
    public int getItemCount() {
        return animales.size(); // Devuelve la cantidad de animales en la lista
    }

    public void actualizarAnimales(List<Animal> nuevosAnimales) {
        // Actualiza la lista de animales y refresca el RecyclerView
        this.animales = nuevosAnimales;
        notifyDataSetChanged();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre, tvDescripcion, tvFavorito; // Vistas de texto
        private ImageView ivFoto; // Imagen del animal
        private Button btnEditar, btnEliminar, btnVerMas; // Botones de acción

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializa todas las vistas del item
            tvNombre = itemView.findViewById(R.id.tv_nombre_animal);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion_animal);
            tvFavorito = itemView.findViewById(R.id.tv_favorito_indicator);
            ivFoto = itemView.findViewById(R.id.iv_foto_animal);
            btnEditar = itemView.findViewById(R.id.btn_editar_animal);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_animal);
            btnVerMas = itemView.findViewById(R.id.btn_ver_mas_animal);
        }

        public void bind(Animal animal) {
            // Asigna los datos del animal a las vistas y configura botones
            tvNombre.setText(animal.getNombre());
            tvDescripcion.setText(animal.getDescripcion());

            //Mostrar indicador de favorito

            if (animal.isEsFavorito()) {
                tvFavorito.setVisibility(View.VISIBLE);
            } else {
                tvFavorito.setVisibility(View.GONE);
            }

            //Carga solo desde la galeria
            cargarImagenDeGaleria(animal.getRutaImagen(), ivFoto);

            // Configura los listeners para los botones del item
            btnEditar.setOnClickListener(v -> listener.onEditarAnimal(animal));
            btnEliminar.setOnClickListener(v -> listener.onEliminarAnimal(animal));
            btnVerMas.setOnClickListener(v -> listener.onVerDetalles(animal));
        }

        private void cargarImagenDeGaleria(String rutaImagen, ImageView imageView) {
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                try {
                    //Solo carga imagen desde la URI
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