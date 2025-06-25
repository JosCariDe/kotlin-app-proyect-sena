package edu.sena.caribeapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import edu.sena.caribeapp.R
import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Estudiante
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.presentation.navigation.AppScreens
import edu.sena.caribeapp.ui.theme.CaribeAppTheme

import edu.sena.caribeapp.ui.theme.Purple40 // Color para la tarjeta de clase
import edu.sena.caribeapp.ui.theme.Purple80 // Color para la tarjeta de clase
import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.Secondary
import edu.sena.caribeapp.ui.theme.PrimaryBackGround
import java.util.Locale // ¡Nueva importación para Locale!

/**
 * Composable para la pantalla principal (HomeScreen).
 * Es la View en el patrón MVVM para el dashboard.
 *
 * @param navController El controlador de navegación.
 * @param viewModel El ViewModel del HomeScreen, inyectado por Hilt.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Observa el estado de la UI del ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Manejo de efectos secundarios (mostrar Toast, etc.)
    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetUiState() // Limpia el estado del error
        }
    }

    // Si está cargando, muestra un indicador de progreso
    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text("Cargando datos...", modifier = Modifier.padding(top = 8.dp))
        }
    } else {
        // Si no está cargando, muestra el contenido de la pantalla
        HomeContent(
            uiState = uiState,
            onTabSelected = viewModel::onTabSelected,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onClassClick = { clase ->
                uiState.estudiante?.let { navController.navigate(AppScreens.ClassScreen.createRoute(clase.id, estudianteId =it.id )) }
            },
            onForumClick = { foro -> /* TODO: Navegar a ForoDetailScreen */ },
            onSimulacroClick = { simulacroConClase ->
                uiState.estudiante?.let { estudiante ->
                    navController.navigate(
                        AppScreens.SimulacroScreen.createRoute(
                            estudianteId = estudiante.id,
                            claseId = simulacroConClase.claseId,
                            simulacroId = simulacroConClase.simulacro.id
                        )
                    )
                }
            }
        )
    }
}

/**
 * Composable que define el contenido visual puro del HomeScreen.
 * Es una función "sin estado" (stateless) para facilitar la vista previa.
 */
@Composable
fun HomeContent(
    uiState: HomeUiState,
    onTabSelected: (HomeTab) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClassClick: (ClaseICFES) -> Unit,
    onForumClick: (Foro) -> Unit,
    onSimulacroClick: (SimulacroConClase) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(PrimaryBackGround) // Fondo general
    ) {
        // Sección de Perfil y Búsqueda
        item {
            ProfileSection(
                estudiante = uiState.estudiante,
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )
        }

        // Sección de Clases ICFES
        item {
            ClassesSection(
                clases = uiState.clasesICFES,
                onClassClick = onClassClick
            )
        }

        // Sección "Mi avance" (Foros/Simulacros)
        item {
            AdvanceSection(
                selectedTab = uiState.selectedTab,
                onTabSelected = onTabSelected,
                foros = uiState.foros,
                simulacros = uiState.simulacros,
                onForumClick = onForumClick,
                onSimulacroClick = onSimulacroClick
            )
        }

        // Espacio al final para que el contenido no quede pegado al borde inferior
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * Composable para la sección superior del perfil y la barra de búsqueda.
 */
@Composable
fun ProfileSection(
    estudiante: Estudiante?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface) // Fondo de la tarjeta superior
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icono de perfil (o imagen de usuario)
            Icon(
                imageVector = Icons.Filled.Person, // Icono de persona
                contentDescription = "Icono de perfil",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape) // Forma circular
                    .background(Primary), // Color de fondo del icono
                tint = Color.White // Color del icono
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = estudiante?.nombreCompleto ?: "Cargando...", // Muestra el nombre del estudiante
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = ("grado: " + estudiante?.grado), // Mensaje estático
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Encuentra tu actividad") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Icono de búsqueda") },
            shape = RoundedCornerShape(24.dp), // Esquinas redondeadas
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Composable para la sección de "Clases ICFES en la que haces parte".
 */
@Composable
fun ClassesSection(
    clases: List<ClaseICFES>,
    onClassClick: (ClaseICFES) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Clases ICFES en la que haces parte",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (clases.isEmpty()) {
            Text("No tienes clases asignadas.", style = MaterialTheme.typography.bodyMedium)
        } else {
            // Usar LazyRow si hubiera muchas clases y quisieras un scroll horizontal
            // Por ahora, un Column simple para mostrar una o dos
            LazyRow {
                items (clases) { clase ->
                    ClassCard(clase = clase, onClick = onClassClick)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

        }
    }
}

/**
 * Composable para una tarjeta individual de Clase ICFES.
 */
@Composable
fun ClassCard(clase: ClaseICFES, onClick: (ClaseICFES) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp), // Altura fija para la tarjeta
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Secondary), // Color de la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onClick(clase) } // Maneja el clic en la tarjeta
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween // Espacio entre elementos
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Computer, // Icono de computadora
                    contentDescription = "Icono de clase",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = clase.nombreClase,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Text(
                text = "${clase.simulacros.count { it.estado == "pendiente" }} Simulacros Pendientes", // Cuenta simulacros pendientes
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
            // Aquí irían las imágenes de los participantes si las tuvieras
            // Row {
            //     clase.participantes.take(3).forEach { participante ->
            //         Image(
            //             painter = rememberAsyncImagePainter(participante.imageUrl),
            //             contentDescription = null,
            //             modifier = Modifier
            //                 .size(30.dp)
            //                 .clip(CircleShape)
            //                 .background(Color.Gray)
            //         )
            //         Spacer(modifier = Modifier.width(-8.dp)) // Superponer imágenes
            //     }
            // }
        }
    }
}

/**
 * Composable para la sección "Mi avance" con pestañas de Foros y Simulacros.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvanceSection(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    foros: List<Foro>,
    simulacros: List<SimulacroConClase>,
    onForumClick: (Foro) -> Unit,
    onSimulacroClick: (SimulacroConClase) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Mi avance",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Pestañas (TabRow)
        PrimaryTabRow(selectedTabIndex = selectedTab.ordinal) {
            HomeTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                    text = { Text(tab.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }) } // ¡Corregido!
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido de la pestaña seleccionada
        when (selectedTab) {
            HomeTab.FOROS -> {
                if (foros.isEmpty()) {
                    Text("No hay foros disponibles.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    foros.forEach { foro ->
                        ForumCard(foro = foro, onClick = onForumClick)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            HomeTab.SIMULACROS -> {
                if (simulacros.isEmpty()) {
                    Text("No hay simulacros disponibles.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    simulacros.forEach { simulacroConClase ->
                        SimulacroCard(simulacroConClase = simulacroConClase, onClick = onSimulacroClick)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

/**
 * Composable para una tarjeta individual de Foro.
 */
@Composable
fun ForumCard(foro: Foro, onClick: (Foro) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onClick(foro) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = foro.nombre,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp)
            )
            Text(
                text = "Creado Por: ${foro.creador}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),

            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Creado el ${foro.fecha.substringBefore("T")}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp),
                    color = Secondary
                )
                Button(
                    onClick = { onClick(foro) },
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                ) {
                    Text("Entrar")
                }
            }
        }
    }
}

/**
 * Composable para una tarjeta individual de Simulacro.
 */
@Composable
fun SimulacroCard(simulacroConClase: SimulacroConClase, onClick: (SimulacroConClase) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { onClick(simulacroConClase) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // <--- AÑADE ESTO
                .padding(16.dp)
        ) {
            Text(
                text = simulacroConClase.simulacro.titulo,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Text(
                text = "Área: ${simulacroConClase.simulacro.area}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Estado: ${simulacroConClase.simulacro.estado.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}", // ¡Corregido!
                style = MaterialTheme.typography.bodyMedium,
                color = when (simulacroConClase.simulacro.estado) {
                    "completado" -> Color.Green
                    "pendiente" -> Color.Red
                    "en progreso" -> Color.Blue
                    else -> Color.Gray
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onClick(simulacroConClase) },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Secondary)
            ) {
                Text("Ver Detalles")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun HomeScreenPreview() {
    CaribeAppTheme {
        val sampleEstudiante = Estudiante(
            id = "1",
            nombreCompleto = "Jose Narvaez",
            correo = "jose@example.com",
            fechaRegistro = "2025-01-01",
            departamento = "Bolívar",
            municipio = "Cartagena",
            grado = "11",
            clasesICFES = listOf(
                ClaseICFES(
                    id = "c1",
                    nombreClase = "Normal 11-B",
                    profesor = "Prof. Ejemplo",
                    foros = listOf(
                        Foro("f1", "Aportación como jóvenes a la sociedad.", "Lic. Maria Marcela", "2025-05-12T10:00:00Z")
                    ),
                    simulacros = listOf(
                        Simulacro("s1", "Simulacro Matemáticas I", "Matemáticas", "pendiente", emptyList(), emptyList(), emptyList()),
                        Simulacro("s2", "Simulacro Ciencias II", "Ciencias", "completado", emptyList(), emptyList(), emptyList())
                    )
                )
            )
        )
        HomeContent(
            uiState = HomeUiState(
                estudiante = sampleEstudiante,
                clasesICFES = sampleEstudiante.clasesICFES ?: emptyList(),
                foros = sampleEstudiante.clasesICFES?.flatMap { it.foros } ?: emptyList(),
                simulacros = sampleEstudiante.clasesICFES?.flatMap { clase ->
                    clase.simulacros.map { simulacro ->
                        SimulacroConClase(simulacro, clase.id)
                    }
                } ?: emptyList(),
                searchQuery = "actividad"
            ),
            onTabSelected = {},
            onSearchQueryChange = {},
            onClassClick = {},
            onForumClick = {},
            onSimulacroClick = {}
        )
    }
}
