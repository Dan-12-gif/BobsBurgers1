package lopez.daniel.bobsburgers.composable

import com.google.gson.annotations.SerializedName

data class Character(
    val id: Int,
    val name: String,
    val gender: String?,
    val image: String?,
    val firstEpisode: String?
)