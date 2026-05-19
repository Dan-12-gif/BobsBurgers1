package lopez.daniel.bobsburgers.composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    var characters by mutableStateOf<List<Character>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                isLoading = true
                characters = RetrofitClient.api.getCharacters()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Ocurrió un error desconocido"
            } finally {
                isLoading = false
            }
        }
    }
}