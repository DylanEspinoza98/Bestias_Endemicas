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

public class MainActivity extends AppCompatActivity implements CarruselAdapter.OnItemClickListener {

    private List<CarruselItem> carouselItems;
    private View mainLayout;

    private ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos la vista principal
        mainLayout = findViewById(R.id.main);
        backgroundImageView = findViewById(R.id.backgroundImageView);

        // Ajustamos la UI a la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Llamamos a los métodos de configuración
        setupCarousel();
        setupSocialMediaButtons();
    }

    // --- MÉTODO SETUPCAROUSEL() COMPLETAMENTE LIMPIO Y CORREGIDO ---
    private void setupCarousel() {
        ViewPager2 viewPager = findViewById(R.id.carouselViewPager);

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);

        // --- 1. Crear la lista de items UNA SOLA VEZ ---
        carouselItems = new ArrayList<>();
        carouselItems.add(new CarruselItem(R.drawable.gato_andino_chile, "Norte", Activity_Norte.class, R.drawable.norte_chile));
        carouselItems.add(new CarruselItem(R.drawable.el_caballito, "Centro", Activity_Centro.class, R.drawable.centro_chile));
        carouselItems.add(new CarruselItem(R.drawable.rana_de_darwin, "Sur", Activity_Sur.class, R.drawable.sur_chile));
        carouselItems.add(new CarruselItem(R.drawable.delfin_chileno, "Austral", Activity_Austral.class, R.drawable.austral_chile));

        // --- 2. Configurar el adaptador y la posición inicial UNA SOLA VEZ ---
        viewPager.setAdapter(new CarruselAdapter(carouselItems, this));
        int middle = Integer.MAX_VALUE / 2;
        viewPager.setCurrentItem(middle - middle % carouselItems.size(), false);

        // --- 3. Configurar la animación UNA SOLA VEZ ---
        CompositePageTransformer compositeTransformer = new CompositePageTransformer();
        compositeTransformer.addTransformer(new MarginPageTransformer(40));
        compositeTransformer.addTransformer(new CarruselTransformer());
        viewPager.setPageTransformer(compositeTransformer);

        // --- 4. Lógica para el cambio de fondo ---
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

    // --- El resto de tus métodos (sin cambios) ---
    private void setupSocialMediaButtons() {
        findViewById(R.id.btnFacebook).setOnClickListener(v -> abrirUrl("https://www.facebook.com/?locale=es_LA"));
        findViewById(R.id.btnInstagram).setOnClickListener(v -> abrirUrl("https://www.instagram.com/"));
        findViewById(R.id.btnX).setOnClickListener(v -> abrirUrl("https://x.com/?lang=es"));
    }

    private void abrirUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onItemClick(CarruselItem item) {
        Intent intent = new Intent(MainActivity.this, item.getActivityToOpen());
        startActivity(intent);
    }
}
