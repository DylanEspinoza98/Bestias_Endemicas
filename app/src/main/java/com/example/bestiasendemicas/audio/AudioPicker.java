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
        void onAudioSelected(Uri audioUri, String fileName);
    }

    private final AppCompatActivity activity;
    private final AudioPickerListener listener;
    private final ActivityResultLauncher<Intent> pickAudioLauncher;

    public AudioPicker(AppCompatActivity activity, AudioPickerListener listener) {
        this.activity = activity;
        this.listener = listener;

        // Registrar el launcher correctamente usando AppCompatActivity
        pickAudioLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult
        );
    }

    // Método público para abrir el selector
    public void selectAudio() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/mpeg"); // Solo mp3
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickAudioLauncher.launch(intent);
    }

    // Maneja el resultado del picker
    private void handleResult(ActivityResult result) {
        if (result != null && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Uri audioUri = result.getData().getData();
            if (audioUri != null) {
                String fileName = getFileNameFromUri(activity, audioUri);
                listener.onAudioSelected(audioUri, fileName);
                Toast.makeText(activity, "✅ Audio seleccionado: " + fileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Obtener el nombre del archivo desde la Uri
    private String getFileNameFromUri(Context context, Uri uri) {
        String name = "audio_desconocido.mp3";
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex >= 0) {
                    name = cursor.getString(nameIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
