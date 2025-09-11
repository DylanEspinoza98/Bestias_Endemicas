package com.example.bestiasendemicas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public static AnimalBottomSheetFragment newInstance(String animalName, String description, int imageResourceId) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ANIMAL_NAME, animalName);
        args.putString(ARG_ANIMAL_DESCRIPTION, description);
        args.putInt(ARG_ANIMAL_IMAGE_RES, imageResourceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_animal_detail, container, false);

        // Configurar elementos del bottom sheet
        TextView titleText = view.findViewById(R.id.tv_bottom_title);
        TextView descriptionText = view.findViewById(R.id.tv_bottom_description);
        ImageView animalImage = view.findViewById(R.id.iv_animal_image);
        Button shareButton = view.findViewById(R.id.btn_share);
        Button favoriteButton = view.findViewById(R.id.btn_favorite);

        // Obtener argumentos
        Bundle args = getArguments();
        if (args != null) {
            titleText.setText(args.getString(ARG_ANIMAL_NAME));
            descriptionText.setText(args.getString(ARG_ANIMAL_DESCRIPTION));

            // Establecer la imagen del animal
            int imageRes = args.getInt(ARG_ANIMAL_IMAGE_RES);
            if (imageRes != 0) {
                animalImage.setImageResource(imageRes);
            }
        }

        // Configurar botones de acción
        shareButton.setOnClickListener(v -> shareAnimalInfo());
        favoriteButton.setOnClickListener(v -> toggleFavorite());

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
