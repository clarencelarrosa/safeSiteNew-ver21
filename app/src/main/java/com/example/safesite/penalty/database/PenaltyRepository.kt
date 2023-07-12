package com.example.safesite.penalty.database

import android.util.Log
import com.example.safesite.database.Register
import kotlinx.coroutines.flow.Flow


class PenaltyRepository (private val dao: PenaltyDatabaseDAO)  {

    suspend fun insert(penalty: Penalty) : Long {
        return dao.insertPenalty(penalty)
    }

    suspend fun delete(penalty: Penalty): Int {
        return dao.deletePenalty(penalty)
    }

    val penalty = dao.displayPenalty()

    //search using date
    suspend fun search(penalty_date: String): Flow<List<Penalty>> {
        return dao.searchPenalty(penalty_date)
    }

    suspend fun update(penalty: Penalty): Int {
        return dao.updatePenalty(penalty)
    }

    suspend fun getPenalTies(penalty_date: String): Penalty? {
        return dao.getPenalties(penalty_date)
    }
}