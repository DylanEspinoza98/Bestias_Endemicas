package com.example.bestiasendemicas;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AnimalBottomSheetFragment extends BottomSheetDialogFragment {

    private SoundPool soundPool;
    private int soundId = -1; // ID del sonido cargado
    private ImageButton btnSonido;

    // Constructor para animales con recurso drawable y sonido
    public static AnimalBottomSheetFragment newInstance(
            String nombre, String descripcion, int imageResId, int soundResId) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putInt("imageResId", imageResId);
        args.putInt("soundResId", soundResId);
        args.putString("imageUri", "");
        fragment.setArguments(args);
        return fragment;
    }

    // Constructor para animales dinámicos con URI
    public static AnimalBottomSheetFragment newInstance(
            String nombre, String descripcion, String imageUri, int soundResId) {
        AnimalBottomSheetFragment fragment = new AnimalBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putInt("imageResId", 0);
        args.putString("imageUri", imageUri != null ? imageUri : "");
        args.putInt("soundResId", soundResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_bottom_sheet, container, false);

        TextView tvNombre = view.findViewById(R.id.tv_animal_nombre);
        TextView tvDescripcion = view.findViewById(R.id.tv_animal_descripcion);
        ImageView ivAnimal = view.findViewById(R.id.iv_animal_imagen);
        btnSonido = view.findViewById(R.id.btn_reproducir_sonido);

        // Configurar SoundPool para sonidos cortos
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        if (getArguments() != null) {
            String nombre = getArguments().getString("nombre");
            String descripcion = getArguments().getString("descripcion");
            int imageResId = getArguments().getInt("imageResId", 0);
            String imageUri = getArguments().getString("imageUri", "");
            int soundResId = getArguments().getInt("soundResId", -1);

            tvNombre.setText(nombre);
            tvDescripcion.setText(descripcion);

            // Cargar imagen según el origen
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


            TextView tvAudio = view.findViewById(R.id.tv_animal_audio);
            // Cargar sonido si existe y mostrar/ocultar botón y texto
            if (soundResId > 0) {
                soundId = soundPool.load(getContext(), soundResId, 1);
                btnSonido.setVisibility(View.VISIBLE);
                tvAudio.setText(getString(R.string.Txt_ReproducirAudio)); // “Reproducir sonido”
            } else {
                btnSonido.setVisibility(View.GONE);
                tvAudio.setText(R.string.Txt_NoAudio); // texto alternativo
            }

        }

        // Reproducir sonido al presionar
        btnSonido.setOnClickListener(v -> {
            if (soundId > 0) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
            } else {
                Log.w("BottomSheet", "No hay sonido para este animal");
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
