package com.example.bestiasendemicas.layouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.bestiasendemicas.R;
import com.example.bestiasendemicas.audio.AudioManagerHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AnimalBottomSheetFragment extends BottomSheetDialogFragment {

    private ImageButton btnSonido;
    private AudioManagerHelper audioHelper;

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_DESCRIPCION = "descripcion";
    private static final String ARG_IMAGE_RES = "imageResId";
    private static final String ARG_IMAGE_URI = "imageUri";
    private static final String ARG_AUDIO_URI = "audioUri";

    // Crear instancia para animales
    public static AnimalBottomSheetFragment newInstance(
            String nombre,
            String descripcion,
            String imageUri,
            String audioUri) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putString("imageUri", imageUri != null ? imageUri : "");
        args.putString("audioUri", audioUri != null ? audioUri : "");
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_bottom_sheet, container, false);

        TextView tvNombre = view.findViewById(R.id.tv_animal_nombre);
        TextView tvDescripcion = view.findViewById(R.id.tv_animal_descripcion);
        ImageView ivAnimal = view.findViewById(R.id.iv_animal_imagen);
        TextView tvAudio = view.findViewById(R.id.tv_animal_audio);
        btnSonido = view.findViewById(R.id.btn_reproducir_sonido);

        audioHelper = new AudioManagerHelper(requireContext());

        if (getArguments() != null) {
            String nombre = getArguments().getString(ARG_NOMBRE);
            String descripcion = getArguments().getString(ARG_DESCRIPCION);
            int imageResId = getArguments().getInt(ARG_IMAGE_RES, 0);
            String imageUri = getArguments().getString(ARG_IMAGE_URI, "");
            String audioUri = getArguments().getString(ARG_AUDIO_URI, "");

            tvNombre.setText(nombre);
            tvDescripcion.setText(descripcion);

            // Cargar imagen según origen
            if (imageResId != 0) {
                ivAnimal.setImageResource(imageResId);
            } else if (!imageUri.isEmpty()) {
                Glide.with(this)
                        .load(imageUri)
                        .placeholder(R.drawable.ic_animal_placeholder)
                        .error(R.drawable.ic_animal_placeholder)
                        .centerCrop()
                        .into(ivAnimal);
            } else {
                ivAnimal.setImageResource(R.drawable.ic_animal_placeholder);
            }

            // Cargar audio y configurar botón
            if (audioUri != null && !audioUri.isEmpty()) {
                audioHelper.loadAudio(audioUri);
                btnSonido.setVisibility(View.VISIBLE);
                tvAudio.setText(getString(R.string.Txt_ReproducirAudio));
            } else {
                btnSonido.setVisibility(View.GONE);
                tvAudio.setText(R.string.Txt_NoAudio);
            }
        }

        // Botón reproducir
        btnSonido.setOnClickListener(v -> audioHelper.play());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        audioHelper.release();
    }
}
