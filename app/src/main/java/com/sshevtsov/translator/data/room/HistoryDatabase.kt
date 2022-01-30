package com.sshevtsov.translator.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sshevtsov.translator.data.room.model.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

}