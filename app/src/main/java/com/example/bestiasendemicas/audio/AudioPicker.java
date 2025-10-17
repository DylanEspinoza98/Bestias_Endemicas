package com.example.bestiasendemicas.audio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AudioPicker {

    public interface AudioPickerListener {
        void onAudioSelected(Uri audioUri, String fileName); // Callback cuando se selecciona un audio
    }

    private final AppCompatActivity activity; // Actividad que lanza el picker
    private final AudioPickerListener listener; // Listener para devolver el audio
    private final ActivityResultLauncher<Intent> pickAudioLauncher; // Launcher para recibir resultado

    public AudioPicker(AppCompatActivity activity, AudioPickerListener listener) {
        this.activity = activity;
        this.listener = listener;

        // Registrar el launcher para manejar el resultado
        pickAudioLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult
        );
    }

    /** Abre el selector de audio */
    public void selectAudio() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Intent para abrir documentos
        intent.setType("audio/mpeg"); // Filtra solo archivos MP3
        intent.addCategory(Intent.CATEGORY_OPENABLE); // Asegura que se pueda abrir
        pickAudioLauncher.launch(intent); // Lanza el selector
    }

    /** Maneja el resultado del selector de audio */
    private void handleResult(ActivityResult result) {
        if (result != null && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Uri audioUri = result.getData().getData(); // Obtiene la URI seleccionada
            if (audioUri != null) {
                String fileName = getFileNameFromUri(activity, audioUri); // Obtiene el nombre del archivo
                listener.onAudioSelected(audioUri, fileName); // Llama al listener con audio
                Toast.makeText(activity, "✅ Audio seleccionado: " + fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** Obtiene el nombre del archivo desde la URI */
    private String getFileNameFromUri(Context context, Uri uri) {
        String name = "audio_desconocido.mp3"; // Nombre por defecto
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME); // Índice del nombre
                if (nameIndex >= 0) {
                    name = cursor.getString(nameIndex); // Asigna el nombre real
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
