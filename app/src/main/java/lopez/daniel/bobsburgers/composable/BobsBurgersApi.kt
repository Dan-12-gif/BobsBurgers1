package lopez.daniel.bobsburgers.composable

import retrofit2.http.GET

interface BobsBurgersApi {
    @GET("characters/")
    suspend fun getCharacters(): List<Character>
}