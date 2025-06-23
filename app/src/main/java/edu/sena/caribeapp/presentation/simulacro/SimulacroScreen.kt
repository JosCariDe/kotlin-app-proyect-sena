package edu.sena.caribeapp.presentation.simulacro

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.presentation.component.utils.MainButton
import edu.sena.caribeapp.presentation.component.utils.SpaceH
import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.PrimaryBackGround

@Composable
fun SimulacroScreen(
    navControlle: NavController,
    viewModel: SimulacroViewModel = hiltViewModel()
) {

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
    }

    @Composable
    fun SimulacroContent(
        uiState: SimulacroUiState,
        onStartSimulacroTouch: (List<Simulacro>) -> Unit
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 25.dp, horizontal = 12.dp)
                .background(PrimaryBackGround)
        ) {
            Text(text = uiState.simulacro?.titulo ?: "Sin titulo",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Primary))

            SpaceH(15.dp)

            Text(text = "La prueba consta de ${uiState.numPreguntas} preguntas")

            SpaceH()

            MainButton(text = "Empezar Simulacro", onClick = {})

        }
    }


}
