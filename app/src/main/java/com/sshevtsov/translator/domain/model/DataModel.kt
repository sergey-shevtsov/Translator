package com.sshevtsov.translator.domain.model

data class DataModel(
    val id: Int,
    val text: String,
    val meanings: List<Meanings>
)