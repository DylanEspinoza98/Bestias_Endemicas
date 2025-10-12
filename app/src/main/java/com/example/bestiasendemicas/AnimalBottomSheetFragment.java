package com.example.bestiasendemicas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import com.bumptech.glide.Glide;
import android.widget.ImageView;
import android.widget.TextView;




import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AnimalBottomSheetFragment extends BottomSheetDialogFragment {


    //Animales base
    public static AnimalBottomSheetFragment newInstance(String nombre, String descripcion, int imageResId) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putInt("imageResId", imageResId); //INT para los recursos drawable
        args.putString("imageUri", "");
        fragment.setArguments(args);
        return fragment;
    }

    //Animales para agregar
    public static AnimalBottomSheetFragment newInstance(String nombre, String descripcion, String imageUri) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putInt("imageResId", 0);
        args.putString("imageUri", imageUri != null ? imageUri : "");//String para URI
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_bottom_sheet, container, false);

        TextView tvNombre = view.findViewById(R.id.tv_animal_nombre);
        TextView tvDescripcion = view.findViewById(R.id.tv_animal_descripcion);
        ImageView ivAnimal = view.findViewById(R.id.iv_animal_imagen);

        if (getArguments() != null) {
            String nombre = getArguments().getString("nombre");
            String descripcion = getArguments().getString("descripcion");
            int imageResId = getArguments().getInt("imageResId", 0);
            String imageUri = getArguments().getString("imageUri", "");

            tvNombre.setText(nombre);
            tvDescripcion.setText(descripcion);



            //Carga imagen según el tipo
            if (imageResId != 0) {
                //Imagen desde recursos (animales hardcodeados)
                Log.d("BottomSheet", "Cargando imagen desde recurso");
                ivAnimal.setImageResource(imageResId);
            } else if (!imageUri.isEmpty()) {
                //Imagen desde URI usando Glide (animales dinámicos)
                Log.d("BottomSheet", "Cargando imagen con Glide desde URI: " + imageUri);
                try {
                    Glide.with(this)
                            .load(imageUri)
                            .placeholder(R.drawable.ic_animal_placeholder)
                            .error(R.drawable.ic_animal_placeholder)
                            .centerCrop()  //Para que se centre
                            .into(ivAnimal);
                } catch (Exception e) {
                    Log.e("BottomSheet", "Error con Glide: " + e.getMessage());
                    ivAnimal.setImageResource(R.drawable.ic_animal_placeholder);
                }
            } else {
                //No hay imagen se usa el placeholder
                Log.d("BottomSheet", "No hay imagen, usando placeholder");
                ivAnimal.setImageResource(R.drawable.ic_animal_placeholder);
            }
        }

        return view;
    }
}
