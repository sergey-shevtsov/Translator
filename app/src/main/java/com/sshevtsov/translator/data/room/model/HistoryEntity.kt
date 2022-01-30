package com.sshevtsov.translator.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "HistoryTable",
    indices = [Index(value = arrayOf("word"), unique = true)]
)
data class HistoryEntity(

    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)
