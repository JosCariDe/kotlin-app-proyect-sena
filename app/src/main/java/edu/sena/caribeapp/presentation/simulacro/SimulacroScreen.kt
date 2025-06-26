package edu.sena.caribeapp.presentation.simulacro

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.sena.caribeapp.domain.estudiantes.model.Simulacro
import edu.sena.caribeapp.presentation.component.utils.MainButton
import edu.sena.caribeapp.presentation.component.utils.SpaceH
import edu.sena.caribeapp.presentation.navigation.AppScreens
import edu.sena.caribeapp.ui.theme.Primary
import edu.sena.caribeapp.ui.theme.PrimaryBackGround
import edu.sena.caribeapp.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulacroScreen(
    navController: NavController,
    viewModel: SimulacroViewModel = hiltViewModel()
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
                        text = uiState.simulacro?.titulo ?: "Simulacro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(
                        AppScreens.HomeScreen.createRoute(
                            estudianteId = uiState.estudiante!!.id
                        )
                    ) }) {
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
                    .background(PrimaryBackGround),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Text("Cargando datos...", modifier = Modifier.padding(top = 8.dp))
                } else if (uiState.simulacro != null) {
                    SimulacroContent(
                        uiState = uiState,
                        onStartSimulacroTouch = { /* TODO: Implementar navegación a QuizScreen */ },
                        navController = navController
                    )
                } else {
                    Text("No se pudo cargar el simulacro.", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SimulacroContent(
    uiState: SimulacroUiState,
    onStartSimulacroTouch: (List<Simulacro>) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.simulacro?.titulo ?: "Simulacro",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary,
                    textAlign = TextAlign.Center
                )
                SpaceH(16.dp)
                Text(
                    text = "Área: ${uiState.simulacro?.area ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                SpaceH(8.dp)
                Text(
                    text = "Estado: ${uiState.simulacro?.estado?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.getDefault()) else it.toString() } ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = when (uiState.simulacro?.estado) {
                        "completado" -> Color.Green
                        "pendiente" -> Color.Red
                        "en progreso" -> Color.Blue
                        else -> Color.Gray
                    }
                )
                SpaceH(16.dp)
                Text(
                    text = "La prueba consta de ${uiState.numPreguntas} preguntas",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                SpaceH(32.dp)
                MainButton(
                    text = "Empezar Simulacro",
                    onClick = {
                        uiState.simulacro?.let { simulacro ->
                            uiState.estudiante?.let { estudiante ->
                                uiState.clase?.let { clase ->
                                    simulacro.listaIdPreguntas?.let { listaIdPreguntas ->
                                        if (listaIdPreguntas.isNotEmpty()) {
                                val firstQuestionId = listaIdPreguntas.first()
                                val totalQuestions = listaIdPreguntas.size
                                            Log.d("SimulacroScreen", "Navegando a QuizScreen con: estudianteId=${estudiante.id}, claseId=${clase.id}, simulacroId=${simulacro.id}, preguntaId=$firstQuestionId, indexActual=0, cantidadPreguntas=$totalQuestions")
                                navController.navigate(
                                    AppScreens.QuizScreen.createRoute(
                                        estudianteId = estudiante.id,
                                        claseId = clase.id,
                                        simulacroId = simulacro.id,
                                        preguntaId = firstQuestionId,
                                        indexActual = "0",
                                        cantidadPreguntas = totalQuestions.toString()
                                    )
                                )
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
