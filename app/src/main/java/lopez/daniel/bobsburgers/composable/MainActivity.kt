package lopez.daniel.bobsburgers.composable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Aquí llamamos a la pantalla que jala toda la API
                    CharacterListScreen()
                }
            }
        }
    }
}

@Composable
fun CharacterListScreen(viewModel: CharacterViewModel = viewModel()) {
    // Aquí revisamos en qué estado está la petición a la API
    when {
        viewModel.isLoading -> {
            // Mientras descarga los personajes de la API, muestra un círculo de carga
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        viewModel.errorMessage != null -> {
            // Si el celular no tiene internet o la API falla, muestra el error
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${viewModel.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        else -> {
            // Si la descarga fue exitosa, dibuja la lista completa de la API
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.characters) { character ->
                    CharacterCard(character)
                }
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    // El diseño de cada tarjeta individual
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Coil jala la imagen directamente desde la URL de la API
            AsyncImage(
                model = character.image,
                contentDescription = "Imagen de ${character.name}",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Sexo: ${character.gender ?: "No especificado"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Primer Episodio: ${character.firstEpisode ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}