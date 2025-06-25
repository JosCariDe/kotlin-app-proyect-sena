package edu.sena.caribeapp.presentation.clase

// app/src/main/java/edu/sena/caribeapp/presentation/class/ClassScreen.kt

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.ui.theme.CaribeAppTheme
import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.Secondary
import java.util.Locale
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

/**
 * Composable para la pantalla de Clase.
 * Muestra los detalles de una clase específica, incluyendo foros y simulacros.
 *
 * @param navController El controlador de navegación.
 * @param viewModel El ViewModel de la pantalla de Clase, inyectado por Hilt.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassScreen(
    navController: NavController,
    viewModel: ClassViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetUiState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${uiState.clase?.nombreClase ?: "Clase"} Simulacros",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background) // Fondo general
            ) {
                if (uiState.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Text("Cargando datos de la clase...", modifier = Modifier.padding(top = 8.dp))
                    }
                } else if (uiState.clase != null) {
                    ClassContent(
                        clase = uiState.clase!!,
                        foros = uiState.foros,
                        simulacros = uiState.simulacros,
                        onForumClick = { foro -> /* TODO: Navegar a ForoDetailScreen */ },
                        onSimulacroClick = { simulacro -> /* TODO: Navegar a SimulacroDetailScreen */ }
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.errorMessage ?: "Error desconocido al cargar la clase.")
                    }
                }
            }
        }
    )
}

/**
 * Composable que define el contenido visual puro de la pantalla de Clase.
 * Es una función "sin estado" (stateless) para facilitar la vista previa.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassContent(
    clase: ClaseICFES,
    foros: List<Foro>,
    simulacros: List<Simulacro>,
    onForumClick: (Foro) -> Unit,
    onSimulacroClick: (Simulacro) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) } // 0 para Pendientes, 1 para Completados

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Fondo general
    ) {
        // Sección de miembros
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Primary),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${clase.participantes.size} miembros", // Usar el tamaño real de participantes
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Imágenes de los participantes (simuladas o reales si hay URLs)
                    clase.participantes.take(3).forEachIndexed { index, _ -> // No necesitamos el objeto participante si no tiene imagen
                        Icon(
                            imageVector = Icons.Filled.Person, // Icono de persona para representar un avatar vacío
                            contentDescription = "Miembro",
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant) // Un color de fondo para el círculo
                                .padding(end = if (index < 2) (-8).dp else 0.dp), // Superponer iconos
                            tint = MaterialTheme.colorScheme.onSurfaceVariant // Color del icono
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Ver miembros",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Pestañas (TabRow)
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            containerColor = Color.Transparent,
            contentColor = Primary
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    Text(
                        "Pendientes",
                        color = if (selectedTabIndex == 0) Color.White else Primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedTabIndex == 0) Primary else Color.Transparent)
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = {
                    Text(
                        "Completados",
                        color = if (selectedTabIndex == 1) Color.White else Primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedTabIndex == 1) Primary else Color.Transparent)
            )
        }

        // Lista de Simulacros
        val filteredSimulacros = when (selectedTabIndex) {
            0 -> simulacros.filter { it.estado == "pendiente" || it.estado == "en progreso" }
            1 -> simulacros.filter { it.estado == "completado" }
            else -> emptyList()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (filteredSimulacros.isEmpty()) {
                item {
                    Text(
                        text = "No hay simulacros en esta categoría.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                items(filteredSimulacros) { simulacro ->
                    //ClassSimulacroCard(simulacro = simulacro, onClick = onSimulacroClick)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }


@Composable
fun ClassSimulacroCard(simulacro: Simulacro, onClick: (Simulacro) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(simulacro) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = simulacro.titulo,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${simulacro.preguntasRespondidas} de ${simulacro.totalPreguntas} Preguntas respondidas",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (simulacro.estado) {
                        "completado" -> "Completado el: ${simulacro.fechaFin?.substringBefore("T") ?: "--/--"}"
                        "pendiente" -> "Comienza el: ${simulacro.fechaInicio?.substringBefore("T") ?: "--/--"}"
                        "en progreso" -> "Disponible Hasta: ${simulacro.fechaFin?.substringBefore("T") ?: "--/--"}"
                        else -> ""
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Icon(
                    imageVector = Icons.Outlined.RadioButtonUnchecked,
                    contentDescription = "Estado",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (simulacro.estado) {
                            "completado" -> Secondary // Verde
                            "pendiente" -> Color.Red // Rojo
                            "en progreso" -> Color(0xFFFDD835) // Amarillo
                            else -> Color.Gray
                        }
                    )
                ) {
                    Text(
                        text = simulacro.estado.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}}
