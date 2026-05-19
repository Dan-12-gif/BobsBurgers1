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
import androidx.compose.ui.tooling.preview.Preview
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
                    CharacterListScreen()
                }
            }
        }
    }
}

@Composable
fun CharacterListScreen(viewModel: CharacterViewModel = viewModel()) {
    when {
        viewModel.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        viewModel.errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${viewModel.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        else -> {
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
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

// ==========================================
// VISTAS PREVIAS (PREVIEWS)
// ==========================================

@Preview(showBackground = true, name = "Vista de Tarjeta")
@Composable
fun CharacterCardPreview() {
    val dummyCharacter = Character(
        id = 1,
        name = "Bob Belcher",
        gender = "Masculino",
        image = "https://bobsburgers-api.herokuapp.com/images/characters/1.jpg",
        firstEpisode = "\"Human Flesh\""
    )

    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CharacterCard(character = dummyCharacter)
        }
    }
}

@Preview(showBackground = true, name = "Vista de Lista Completa", showSystemUi = true)
@Composable
fun CharacterListPreview() {
    val dummyList = listOf(
        Character(1, "Bob Belcher", "Masculino", "", "\"Human Flesh\""),
        Character(2, "Linda Belcher", "Femenino", "", "\"Human Flesh\""),
        Character(3, "Tina Belcher", "Femenino", "", "\"Human Flesh\""),
        Character(4, "Gene Belcher", "Masculino", "", "\"Human Flesh\""),
        Character(5, "Louise Belcher", "Femenino", "", "\"Human Flesh\""),
        Character(6, "Teddy", "Masculino", "", "\"Crawl Space\"")
    )

    MaterialTheme {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyList) { character ->
                CharacterCard(character)
            }
        }
    }
}