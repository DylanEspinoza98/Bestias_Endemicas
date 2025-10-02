package com.example.bestiasendemicas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AnimalBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_ANIMAL_NAME = "animal_name";
    private static final String ARG_ANIMAL_DESCRIPTION = "animal_description";
    private static final String ARG_ANIMAL_IMAGE_RES = "animal_image_res";

    // MÉTODO 1: Para animales hardcodeados (con drawable resource - int)
    public static AnimalBottomSheetFragment newInstance(String nombre, String descripcion, int imageResId) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putInt("imageResId", imageResId);  // ← INT para drawable resources
        args.putString("imageUri", "");
        fragment.setArguments(args);
        return fragment;
    }

    // MÉTODO 2: Para animales dinámicos (con URI de galería - String)
    public static AnimalBottomSheetFragment newInstance(String nombre, String descripcion, String imageUri) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putInt("imageResId", 0);
        args.putString("imageUri", imageUri != null ? imageUri : ""); // ← STRING para URI
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

            // Cargar imagen según el tipo
            if (imageResId != 0) {
                // Imagen desde recursos (animales hardcodeados)
                ivAnimal.setImageResource(imageResId);
            } else if (!imageUri.isEmpty()) {
                // Imagen desde URI de galería (animales dinámicos)
                try {
                    Uri uri = Uri.parse(imageUri);
                    ivAnimal.setImageURI(uri);
                } catch (Exception e) {
                    ivAnimal.setImageResource(R.drawable.ic_animal_placeholder);
                }
            } else {
                // Placeholder por defecto
                ivAnimal.setImageResource(R.drawable.ic_animal_placeholder);
            }
        }

        return view;
    }

    private void shareAnimalInfo() {
        Bundle args = getArguments();
        if (args != null) {
            String animalName = args.getString(ARG_ANIMAL_NAME);
            String description = args.getString(ARG_ANIMAL_DESCRIPTION);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Animal Endémico de Chile: " + animalName);
            shareIntent.putExtra(Intent.EXTRA_TEXT, animalName + "\n\n" + description + "\n\nCompartido desde Bestias Endémicas");
            startActivity(Intent.createChooser(shareIntent, "Compartir información del " + animalName));
        }
    }

    private void toggleFavorite() {
        Bundle args = getArguments();
        if (args != null) {
            String animalName = args.getString(ARG_ANIMAL_NAME);
            Toast.makeText(getContext(), animalName + " agregado a favoritos", Toast.LENGTH_SHORT).show();
        }
    }
}
