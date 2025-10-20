<h1 align="center">🐾 Bestias Endémicas</h1>

<!-- FOTO: portada o logo principal del proyecto aquí -->

<p align="center">
  <em>Aplicación Android que muestra animales endémicos de Chile, clasificados por región.</em>
</p>

---

## 🐆 Descripción General

“Bestias Endémicas” es una aplicación Android que muestra animales endémicos de Chile por región.  
Incluye tarjetas de animales predefinidos y un sistema CRUD dinámico con SQLite.  
Permite ver detalles, filtrar por tipo, marcar favoritos y agregar o editar animales.  
Incluye la opción de reproducir sonidos de cada animal, tanto estáticos (RAW) como dinámicos (MP3 seleccionados).

---

## 🗂️ Estructura del Proyecto

La aplicación se divide en dos carpetas principales:

- **java/** → Lógica de funcionamiento.
- **res/** → Recursos visuales y de texto.

---

### 📦 Estructura Java real del proyecto

  <pre>
com.example.bestiasendemicas/
├── adapter/
│   ├── AnimalAdapter.java
│   └── CarruselAdapter.java
├── audio/
│   ├── AudioManagerHelper.java
│   └── AudioPicker.java
├── database/
│   ├── AnimalContract.java
│   ├── AnimalCrud.java
│   └── AnimalDBHelper.java
├── layouts/
│   ├── Activity_Austral.java
│   ├── Activity_Centro.java
│   ├── Activity_Norte.java
│   ├── Activity_Sur.java
│   ├── AddEditAnimal.java
│   └── AnimalBottomSheetFragment.java
├── model/
│   ├── Animal.java
│   └── Region.java
├── CarruselItem.java
├── CarruselTransformer.java
└── MainActivity.java
  </pre>

---

### 🧩 Funcionalidades principales

#### 🦁 MainActivity
- Muestra cuatro regiones: Norte, Centro, Sur, Austral.
- Navegación mediante **carrusel visual** (ViewPager2).



![Image](https://github.com/user-attachments/assets/f9671a30-6028-4caa-b285-ff310351070d)


---

#### 🐻 Activities regionales

- 5 animales estáticos (hardcodeados) por región con datos y “Ver más”.
- Filtros por tipo y favoritos (ChipGroup).
- `RecyclerView` para animales dinámicos (base de datos).
- Soporte de audio para animales estáticos (`res/raw`) y dinámicos (MP3).
- “Ver más” abre AnimalBottomSheetFragment.

### Ejemplo visual del Activity regional

<img width="302" height="669" alt="Activity_Regional" src="https://github.com/user-attachments/assets/4ba33768-6b26-4b63-b42a-a89834367b92" />

---

#### 📝 AddEditAnimal

- Formulario para ingresar animal: nombre, descripción, tipo, región, favorito, imagen, sonido.
- Guarda datos y recursos en SQLite.
- Selector para sonido usando AudioPicker.
- Compatibilidad con audio interno (RAW) y externo (MP3).

### Ejemplo visual de AddEditAnimal

<img width="270" height="791" alt="AddEditAnimal" src="https://github.com/user-attachments/assets/bbe84c6f-cffe-45c4-b888-1f53d8d2d06c" />


---

#### 🎧 Sistema de Sonido

- AudioManagerHelper reproduce .mp3 y recursos internos (RAW).
- AudioPicker permite elegir sonido en el dispositivo.
- BottomSheet permite reproducir sonido del animal si está presente.

<!-- FOTO: Ejemplo UI audio -->

---

#### 📄 AnimalBottomSheetFragment

- Panel inferior con imagen, descripción, reproductor de sonido.
- Usado desde todas las activities para mostrar detalle de animales.

### Ejemplo visual del AnimalBottomSheetFragment

<img width="371" height="489" alt="AnimalBottomSheetFragment" src="https://github.com/user-attachments/assets/f9dbe5a3-8469-49db-8f5e-7ae46cd799e8" />


---

### 💾 Base de datos y modelos

- AnimalContract.java: define nombres de tablas y columnas.
- AnimalDBHelper.java: crea y actualiza la base de datos.
- AnimalCrud.java: opera CRUD completo y mapeo de datos.
- Modelos: Animal.java y Region.java.

### Ejemplo visual de la base de datos

<img width="1060" height="391" alt="MERBaseDeDatos" src="https://github.com/user-attachments/assets/f78986e2-cd0f-4da1-ab09-6945cd97fb5e" />



---

### 🎨 Recursos de res/

| Carpeta   | Descripción                                             |
|-----------|---------------------------------------------------------|
| layout    | Layouts XML para interfaces y fragmentos                |
| values    | strings.xml, colors.xml, dimens.xml, styles.xml         |
| drawable  | Imágenes e iconos                                       |
| raw       | Archivos de audio para animales estáticos               |

---

### 🧭 Permisos y configuración

En AndroidManifest.xml:
- Declaración de Activities.
- Permiso galería/audio.

---

### 👥 Integrantes
  
- NramirezAndroid = Nicolás Ramírez
- alecari = Alejandro Carilao
- DylanEspinoza98 = Dylan Espinoza

---

### 🧪 Pruebas
Todas las pruebas fueron hechas en un teléfono Google Pixel 6 Pro con la API 31.

### Ejemplo visual del Google Pixel 6 Pro

<img width="576" height="576" alt="pixel 6 pro" src="https://github.com/user-attachments/assets/33b772a5-289a-4aa9-9b16-4f9de8f993fa" />
