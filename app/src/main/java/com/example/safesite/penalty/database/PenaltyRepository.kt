package com.example.safesite.penalty.database

import kotlinx.coroutines.flow.Flow


class PenaltyRepository (private val dao: PenaltyDatabaseDAO)  {

    suspend fun insert(penalty: Penalty) : Long {
        return dao.insertPenalty(penalty)
    }

    suspend fun delete(penalty: Penalty): Int {
        return dao.deletePenalty(penalty)
    }

    val penalty = dao.displayPenalty()
    suspend fun search(penalty_date: String): Flow<List<Penalty>> {

        return dao.searchPenalty(penalty_date)
    }

    suspend fun update(penalty: Penalty): Int {
        return dao.updatePenalty(penalty)
    }
}