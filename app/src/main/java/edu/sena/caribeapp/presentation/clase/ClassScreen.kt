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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import edu.sena.caribeapp.R
import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.presentation.home.HomeTab
import edu.sena.caribeapp.ui.theme.CaribeAppTheme
import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.Purple40
import java.util.Locale

/**
 * Composable para la pantalla de Clase.
 * Muestra los detalles de una clase específica, incluyendo foros y simulacros.
 *
 * @param navController El controlador de navegación.
 * @param viewModel El ViewModel de la pantalla de Clase, inyectado por Hilt.
 */
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
        // Muestra el contenido de la clase si se cargó correctamente
        ClassContent(
            navController = navController, // Pasamos el navController
            clase = uiState.clase!!,
            foros = uiState.foros,
            simulacros = uiState.simulacros,
            onForumClick = { foro -> /* TODO: Navegar a ForoDetailScreen */ },
            onSimulacroClick = { simulacro -> /* TODO: Navegar a SimulacroDetailScreen */ }
        )
    } else {
        // Muestra un mensaje de error si no se pudo cargar la clase
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = uiState.errorMessage ?: "Error desconocido al cargar la clase.")
        }
    }
}

/**
 * Composable que define el contenido visual puro de la pantalla de Clase.
 * Es una función "sin estado" (stateless) para facilitar la vista previa.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassContent(
    navController: NavController, // Recibe el NavController
    clase: ClaseICFES,
    foros: List<Foro>,
    simulacros: List<Simulacro>,
    onForumClick: (Foro) -> Unit,
    onSimulacroClick: (Simulacro) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary) // Fondo verde
    ) {
        // Barra superior con botón de retroceso y nombre de la clase
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) { // Botón de retroceso
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = clase.nombreClase + " Simulacros", // Nombre de la clase
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Sección de miembros (simulada)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("12 miembros")
            Row {
                // Aquí irían las imágenes de los participantes si las tuvieras
                // Image(...)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Ver miembros", modifier = Modifier.size(16.dp))
        }

        // Pestañas (TabRow)
        PrimaryTabRow(selectedTabIndex = 0) { // Por ahora, siempre selecciona la primera pestaña
            Tab(selected = true, onClick = { /* TODO: Manejar selección de pestaña "Pendientes" */ }, text = { Text("Pendientes") })
            Tab(selected = false, onClick = { /* TODO: Manejar selección de pestaña "Completados" */ }, text = { Text("Completados") })
        }

        // Lista de Simulacros (simulada)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(simulacros) { simulacro ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    onClick = { onSimulacroClick(simulacro) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = simulacro.titulo,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "19 de 20 Preguntas respondidas", // Datos simulados
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            text = "Disponible Hasta: --/--", // Datos simulados
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "In Progress", // Estado simulado
                                style = MaterialTheme.typography.bodySmall,
                                color = Primary // Color simulado
                            )
                            // Icono de selección (simulado)
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Reemplaza con un icono real
                                contentDescription = "Seleccionar",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


