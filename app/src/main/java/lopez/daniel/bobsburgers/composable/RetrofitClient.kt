package lopez.daniel.bobsburgers.composable

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://bobsburgers-api.herokuapp.com/"

    val api: BobsBurgersApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BobsBurgersApi::class.java)
    }
}