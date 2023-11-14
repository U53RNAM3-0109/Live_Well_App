package com.example.livewellrestructured.network

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LiveWellAppData (
    val sections: @Serializable List<Map<String, List<Map<String,String>>>>
    )