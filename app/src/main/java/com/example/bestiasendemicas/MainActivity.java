package com.example.bestiasendemicas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

// --- Imports necesarios que probablemente faltaban ---
import com.example.bestiasendemicas.layouts.Activity_Austral;
import com.example.bestiasendemicas.layouts.Activity_Centro;
import com.example.bestiasendemicas.layouts.Activity_Norte;
import com.example.bestiasendemicas.layouts.Activity_Sur;
import com.example.bestiasendemicas.adapter.CarruselAdapter;

import java.util.ArrayList;
import java.util.List;

// 1. IMPLEMENTA LA INTERFAZ DEL ADAPTADOR
public class MainActivity extends AppCompatActivity implements CarruselAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // EdgeToEdge se maneja mejor con el WindowInsetsListener
        setContentView(R.layout.activity_main);

        // --- CÓDIGO PARA AJUSTAR LA UI A LA PANTALLA (CORRECTAMENTE DENTRO DE ONCREATE) ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- LLAMADAS A MÉTODOS DE CONFIGURACIÓN ---
        setupCarousel();
        setupSocialMediaButtons();
    }

    private void setupCarousel() {
        ViewPager2 viewPager = findViewById(R.id.carouselViewPager);

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);


        // --- Crear la lista de items para el carrusel ---
        // Asegúrate de que las clases Activity_Norte, etc., y los drawables existan
        List<CarruselItem> carouselItems = new ArrayList<>();
        carouselItems.add(new CarruselItem(R.drawable.gato_andino_chile, "Norte", Activity_Norte.class));
        carouselItems.add(new CarruselItem(R.drawable.el_caballito, "Centro", Activity_Centro.class));
        carouselItems.add(new CarruselItem(R.drawable.rana_de_darwin, "Sur", Activity_Sur.class));
        carouselItems.add(new CarruselItem(R.drawable.delfin_chileno, "Austral", Activity_Austral.class));

        // 2. CONFIGURAR EL ADAPTADOR PASANDO 'THIS' COMO LISTENER
        viewPager.setAdapter(new CarruselAdapter(carouselItems, this));
        int middle = Integer.MAX_VALUE / 2;
        viewPager.setCurrentItem(middle - middle % carouselItems.size(), false);

        // --- Configurar el transformador de animación ---
        CompositePageTransformer compositeTransformer = new CompositePageTransformer();
        compositeTransformer.addTransformer(new MarginPageTransformer(40));
        compositeTransformer.addTransformer(new CarruselTransformer());
        viewPager.setPageTransformer(compositeTransformer);
    }

    // --- NUEVO MÉTODO PARA AGRUPAR LA LÓGICA DE LOS BOTONES SOCIALES ---
    private void setupSocialMediaButtons() {
        // Facebook
        findViewById(R.id.btnFacebook).setOnClickListener(v -> abrirUrl("https://www.facebook.com/?locale=es_LA"));

        // Instagram
        findViewById(R.id.btnInstagram).setOnClickListener(v -> abrirUrl("https://www.instagram.com/"));

        // Twitter/X
        findViewById(R.id.btnX).setOnClickListener(v -> abrirUrl("https://x.com/?lang=es"));
    }

    private void abrirUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    // 3. IMPLEMENTACIÓN OBLIGATORIA DEL MÉTODO DE LA INTERFAZ
    @Override
    public void onItemClick(CarruselItem item) {
        // La lógica para abrir la Activity ahora está aquí, que es el lugar correcto
        Intent intent = new Intent(MainActivity.this, item.getActivityToOpen());
        startActivity(intent);
    }
}
