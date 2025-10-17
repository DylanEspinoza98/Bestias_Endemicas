package com.example.bestiasendemicas.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

public class AudioManagerHelper {

    private MediaPlayer mediaPlayer;
    private final Context context;

    public AudioManagerHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Carga un audio desde URI. Solo MP3.
     * @param soundUri URI del audio (puede ser externa o interna)
     */
    public void loadAudio(String soundUri) {
        release(); // liberar cualquier MediaPlayer previo

        if (soundUri == null || soundUri.isEmpty()) {
            Toast.makeText(context, "No hay audio disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!soundUri.toLowerCase().endsWith(".mp3")) {
            Toast.makeText(context, "Solo se permiten archivos MP3", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Uri uri = Uri.parse(soundUri);
            mediaPlayer = MediaPlayer.create(context, uri);
            if (mediaPlayer == null) {
                Toast.makeText(context, "Error al cargar el audio", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "No se pudo cargar el audio", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /** Reproduce el audio cargado desde el inicio */
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        } else {
            Toast.makeText(context, "No hay audio cargado", Toast.LENGTH_SHORT).show();
        }
    }

    /** Detiene y libera el audio */
    public void release() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /** Indica si hay audio cargado */
    public boolean hasAudio() {
        return mediaPlayer != null;
    }
}
