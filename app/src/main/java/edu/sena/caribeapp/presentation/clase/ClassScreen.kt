package edu.sena.caribeapp.presentation.clase

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.sena.caribeapp.domain.estudiantes.model.ClaseICFES
import edu.sena.caribeapp.domain.estudiantes.model.Foro
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro

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
@Composable
fun ClassContent(
    clase: ClaseICFES,
    foros: List<Foro>,
    simulacros: List<Simulacro>,
    onForumClick: (Foro) -> Unit,
    onSimulacroClick: (Simulacro) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Nombre de la clase: ${clase.nombreClase}")
        Text(text = "Profesor: ${clase.profesor}")

        Text("Foros:")
        foros.forEach { foro ->
            Text(text = foro.nombre)
        }

        Text("Simulacros:")
        simulacros.forEach { simulacro ->
            Text(text = simulacro.titulo)
        }
    }
}
