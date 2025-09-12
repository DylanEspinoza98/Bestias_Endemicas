package com.example.bestiasendemicas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton cambiarPantalla1 = findViewById(R.id.btnNorte);
        cambiarPantalla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Norte.class);
                startActivity(intent);
            }
        });

        ImageButton cambiarPantalla2 = findViewById(R.id.btnCentro);
        cambiarPantalla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Centro.class);
                startActivity(intent);
            }
        });

        ImageButton cambiarPantalla3 = findViewById(R.id.btnSur);
        cambiarPantalla3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Sur.class);
                startActivity(intent);
            }
        });

        ImageButton cambiarPantalla4 = findViewById(R.id.btnAustral);
        cambiarPantalla4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Austral.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Botones para redes sociales

        // Facebook
        ImageButton btnFacebook = findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(v -> {
            String url = "https://www.facebook.com/?locale=es_LA";
            abrirUrl(url);
        });

        // Instagram
        ImageButton btnInstagram = findViewById(R.id.btnInstagram);
        btnInstagram.setOnClickListener(v -> {
            String url = "https://www.instagram.com/";
            abrirUrl(url);
        });

        // Twitter/X
        ImageButton btnTwitter = findViewById(R.id.btnX);
        btnTwitter.setOnClickListener(v -> {
            String url = "https://x.com/?lang=es";
            abrirUrl(url);
        });
    }

    private void abrirUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
