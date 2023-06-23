package com.example.safesite.penalty.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PenaltyDatabaseDAO {

    @Insert
    suspend fun insertPenalty(penalty: Penalty) : Long

    @Query("DELETE FROM penalty_table")
    suspend fun deleteAll() : Int

    @Update
    suspend fun updatePenalty(penalty: Penalty) : Int

    @Delete
    suspend fun deletePenalty(penalty: Penalty) : Int

    @Query("SELECT * FROM penalty_table where penaltyDate = :penaltyDate")
    fun searchPenalty(penaltyDate: String): Flow<List<Penalty>>

    @Query("SELECT * FROM penalty_table  ORDER BY penaltyId DESC")
    fun displayPenalty(): Flow<List<Penalty>>
}