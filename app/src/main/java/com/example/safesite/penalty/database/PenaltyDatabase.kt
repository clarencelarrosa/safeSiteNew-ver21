package com.example.safesite.penalty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Penalty::class], version = 2, exportSchema = false)
abstract class PenaltyDatabase : RoomDatabase() {
    abstract val penaltyDatabaseDao: PenaltyDatabaseDAO
    companion object {
        @Volatile
        private var INSTANCE: PenaltyDatabase? = null
        fun getInstance(context: Context): PenaltyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PenaltyDatabase::class.java,
                        "penalties_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}