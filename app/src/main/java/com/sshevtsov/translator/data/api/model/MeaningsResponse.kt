package com.sshevtsov.translator.data.api.model

import com.google.gson.annotations.SerializedName

data class MeaningsResponse(
    @field:SerializedName("translation") val translation: TranslationResponse?,
    @field:SerializedName("imageUrl") val imageUrl: String?,
    @field:SerializedName("soundUrl") val soundUrl: String?
)