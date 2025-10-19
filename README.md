“Bestias Endémicas” es una aplicación Android que muestra animales endémicos de Chile por región.
Incluye tarjetas de animales predefinidos y un sistema CRUD dinámico con SQLite.
Permite ver detalles, filtrar por tipo, marcar favoritos y agregar o editar animales.

![Image](https://github.com/user-attachments/assets/e063e691-80e8-43cb-9ec0-79ec49f00a29)

La estructura principal de la aplicación se puede dividir en 2 grandes carpetas que contienen el funcionamiento y
el diseño de esta, siendo las carpetas "java" (donde se encuentra com/example/bestiasendemicas que contiene los activitys esenciales)
y "res" (donde se encuentran todos los recursos visuales y de texto de la aplicación).

“java”

Contiene el paquete com.example.bestiasendemicas con subpaquetes:

adapter → AnimalAdapter.java

database → AnimalContract.java, AnimalCrud.java, AnimalDBHelper.java

model → Animal.java, Region.java

Actividades esenciales:

MainActivity.java

Activity_Norte.java

Activity_Centro.java

Activity_Sur.java

Activity_Austral.java

AddEditAnimal.java

AnimalBottomSheetFragment.java

“res”

Diseños XML en res/layout:

activity_main.xml

activity_norte.xml

activity_centro.xml

activity_sur.xml

activity_austral.xml

activity_add_edit_animal.xml

fragment_animal_bottom_sheet.xml

Recursos de valores en res/values:

strings.xml (nombres y descripciones de animales, títulos, etiquetas).

dimens.xml (estructura para el selector de region y de tipo de animal).

colors.xml, styles.xml (paleta de colores y estilos).

layouts de spinner en spinner_item_black_text.xml.

MainActivity
Presenta 4 botones regionales: Norte, Centro, Sur, Austral. Cada botón utiliza el Activity correspondiente.

Activity_Norte / Activity_Centro / Activity_Sur / Activity_Austral
– 5 animales estaticos propios de la región (imagen, nombre, texto corto, botón “Ver más”).
– ChipGroup para filtrar por Todos, Terrestre, Volador, Acuático y Favoritos.
– RecyclerView + AnimalAdapter para animales dinámicos obtenidos de SQLite.
– FloatingActionButton para abrir AddEditAnimal con EXTRA_REGION_ID.
– Botón “Volver” que finaliza la Activity.
– Al pulsar “Ver más” se abre AnimalBottomSheetFragment con detalles completos.

AddEditAnimal
Formulario para añadir o editar:
– EditText nombre y descripción.
– Spinners región (habilitado solo en edición) y tipo.
– CheckBox favorito.
– Selector de imagen con ActivityResultContracts.PickVisualMedia.
– Preview de imagen en ImageView.
– Botones “Guardar”/“Actualizar” y “Cancelar”.

AnimalBottomSheetFragment
BottomSheetDialogFragment que muestra:
– Imagen amplia.
– Nombre y descripción completa.
– Se usa desde todas las Activities para detalles.

AnimalAdapter
Adaptador de RecyclerView:
– Entrega informacion a item_animal.xml.
– Vincula datos de Animal (modelo) a vistas.
– Llamado a OnAnimalActionListener para editar, eliminar y ver detalles.

AnimalCrud
Clase SQLiteOpenHelper:
– Define tablas animales y regiones.
– CRUD: insertar, actualizar, eliminar, obtenerAnimalesPorRegion (usa Cursor), obtenerTodasLasRegiones.
– Lee columnas del Cursor y mapea a objetos Animal.

AnimalDBHelper
Gestiona la creación y actualización de la base de datos bestias_endemicas.db:
– Versión de base 3: añade columna tipo si la versión anterior era menor.
– Inserta registros en tabla regiones (‘Norte’, ‘Centro’, ‘Sur’, ‘Austral’) en onCreate().
– Actualiza registros la base de datos con onUpgrade().
– Habilita las llaves foraneas (foreign_keys=ON) en onConfigure().

AnimalContract
Clase final que define constantes para nombres de tablas y columnas.

Model
– Animal.java: tabla animal con id, nombre, descripción, rutaImagen, regionId, esFavorito, tipo.
– Region.java: tabla region con id y nombre.

AnimalManifest
Se declara los activitys y se añade los permisos necesarios para usar la galeria.

Integrantes:
NramirezAndroid = Nicolás Ramírez
alecari = Alejandro Carilao
DylanEspinoza98 = Dylan Espinoza

Todos las pruebas fueron hechas en un telefono pixel 6 pro con la api 31
