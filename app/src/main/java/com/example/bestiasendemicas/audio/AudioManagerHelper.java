package com.example.bestiasendemicas.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

public class AudioManagerHelper {

    private MediaPlayer mediaPlayer; //Reproduce el audio
    private final Context context; //Contexto seguro
    private MediaPlayer mediaPlayer; // Reproduce el audio
    private final Context context; // Reproduce el audio


    public AudioManagerHelper(Context context) {
        this.context = context.getApplicationContext(); // Inicializa el contexto seguro
    }

    /**
     * Carga un audio desde una URI (soporta archivos externos .mp3, URI content:// y recursos internos android.resource://).
     * @param soundUri URI del audio (puede ser externa, interna o de recurso RAW)
     */
    public void loadAudio(String soundUri) {
        release(); //Libera cualquier MediaPlayer previo

        if (soundUri == null || soundUri.isEmpty()) {
            Toast.makeText(context, "No hay audio disponible", Toast.LENGTH_SHORT).show(); // Aviso si no hay URI
            return;
        }

        if (soundUri.startsWith("android.resource://")) {
            //Verifica si es un recurso RAW interno
            try {
                Uri uri = Uri.parse(soundUri);
                mediaPlayer = MediaPlayer.create(context, uri);
                if (mediaPlayer == null) {
                    Toast.makeText(context, "Error al cargar el audio del recurso", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "No se pudo cargar el audio del recurso", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (soundUri.toLowerCase().endsWith(".mp3") || soundUri.startsWith("content://") || soundUri.startsWith("file://")) {
            //Verifica si es un archivo MP3 externo o de almacenamiento
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
        } else {
            Toast.makeText(context, "Formato de audio no compatible", Toast.LENGTH_SHORT).show();

        if (!soundUri.toLowerCase().endsWith(".mp3")) {
            Toast.makeText(context, "Solo se permiten archivos MP3", Toast.LENGTH_SHORT).show(); // Verifica extensión
            return;
        }

        try {
            Uri uri = Uri.parse(soundUri);
            mediaPlayer = MediaPlayer.create(context, uri); // Crea MediaPlayer con URI
            if (mediaPlayer == null) {
                Toast.makeText(context, "Error al cargar el audio", Toast.LENGTH_SHORT).show(); // Error si falla
            }
        } catch (Exception e) {
            Toast.makeText(context, "No se pudo cargar el audio", Toast.LENGTH_SHORT).show(); // Error general
            e.printStackTrace();
        }
    }

    /** Reproduce el audio cargado desde el inicio */
    public void play() {
        if (mediaPlayer != null) {

            mediaPlayer.seekTo(0); //Reinicia el audio
            mediaPlayer.start(); //Inicia la reproducción
            mediaPlayer.seekTo(0); // Reinicia el audio
            mediaPlayer.start(); // Inicia la reproducción del audio
        } else {
            Toast.makeText(context, "No hay audio cargado", Toast.LENGTH_SHORT).show(); // Aviso si no hay audio
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