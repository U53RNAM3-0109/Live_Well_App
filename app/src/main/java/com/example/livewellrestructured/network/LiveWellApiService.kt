package com.example.livewellrestructured.network
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://www.livewellinbraunton.github.io"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface LiveWellApiService {
    @GET("AppData")
    suspend fun getLiveWellData() : LiveWellAppData
}

object LiveWellApi {
    val retrofitService : LiveWellApiService by lazy {
    retrofit.create(LiveWellApiService::class.java)}
    }
