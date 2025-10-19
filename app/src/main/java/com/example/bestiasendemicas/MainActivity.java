package com.example.bestiasendemicas;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.example.bestiasendemicas.layouts.Activity_Austral;
import com.example.bestiasendemicas.layouts.Activity_Centro;
import com.example.bestiasendemicas.layouts.Activity_Norte;
import com.example.bestiasendemicas.layouts.Activity_Sur;


public class MainActivity extends AppCompatActivity implements CarruselAdapter.OnItemClickListener {

    private List<CarruselItem> carouselItems;
    private View mainLayout;

    private ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // EdgeToEdge se maneja mejor con el WindowInsetsListener
        setContentView(R.layout.activity_main);

        // Inicializamos la vista principal
        mainLayout = findViewById(R.id.main);
        backgroundImageView = findViewById(R.id.backgroundImageView);

        // --- CÓDIGO PARA AJUSTAR LA UI A LA PANTALLA (CORRECTAMENTE DENTRO DE ONCREATE) ---
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- LLAMADAS A MÉTODOS DE CONFIGURACIÓN ---
        setupCarousel();
        setupSocialMediaButtons();

    }




    // --- MÉTODO SETUPCAROUSEL() COMPLETAMENTE LIMPIO Y CORREGIDO ---
    private void setupCarousel() {
        ViewPager2 viewPager = findViewById(R.id.carouselViewPager);

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);

        // --- Crear la lista de items para el carrusely su fondo ---
        // Asegurando de que las clases Activity_Norte, etc., y los drawables existan
        carouselItems = new ArrayList<>();
        carouselItems.add(new CarruselItem(R.drawable.gato_andino_chile, "Norte", Activity_Norte.class, R.drawable.norte_chile));
        carouselItems.add(new CarruselItem(R.drawable.el_caballito, "Centro", Activity_Centro.class, R.drawable.centro_chile));
        carouselItems.add(new CarruselItem(R.drawable.rana_de_darwin, "Sur", Activity_Sur.class, R.drawable.sur_chile));
        carouselItems.add(new CarruselItem(R.drawable.delfin_chileno, "Austral", Activity_Austral.class, R.drawable.austral_chile));

        // --- CONFIGURAR EL ADAPTADOR PASANDO 'THIS' COMO LISTENER---
        viewPager.setAdapter(new CarruselAdapter(carouselItems, this));
        int middle = Integer.MAX_VALUE / 2;
        viewPager.setCurrentItem(middle - middle % carouselItems.size(), false);

        // ---  Configurar el transformador de animación ---
        CompositePageTransformer compositeTransformer = new CompositePageTransformer();
        compositeTransformer.addTransformer(new MarginPageTransformer(40));
        compositeTransformer.addTransformer(new CarruselTransformer());
        viewPager.setPageTransformer(compositeTransformer);

        // ---  Configurar el cambio de fondo ---
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                int realPosition = position % carouselItems.size();
                CarruselItem currentItem = carouselItems.get(realPosition);

                Drawable newBackground = ContextCompat.getDrawable(MainActivity.this, currentItem.getBackgroundImageResource());
                Drawable oldBackground = backgroundImageView.getDrawable();

                if (oldBackground == null) {
                    oldBackground = new ColorDrawable(android.graphics.Color.TRANSPARENT);
                }

                Drawable[] layers = {oldBackground, newBackground};
                TransitionDrawable transition = new TransitionDrawable(layers);

                backgroundImageView.setImageDrawable(transition);
                transition.startTransition(500);
            }
        });

        // --- 5. Establecer el fondo inicial ---
        // Esta línea ahora usará la lista correcta, que SÍ tiene elementos.
        backgroundImageView.setImageResource(carouselItems.get(0).getBackgroundImageResource());

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