package com.sshevtsov.translator.data.room

import androidx.room.*
import com.sshevtsov.translator.data.room.model.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryTable")
    suspend fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryTable WHERE word = :word")
    suspend fun getDataByWord(word: String): HistoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<HistoryEntity>)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryTable")
    suspend fun clear()

}