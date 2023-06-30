package com.example.safesite.record

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Record::class], version=1)
abstract class RecordDatabase:RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var instance: RecordDatabase?=null
        fun getDatabase(context: Context): RecordDatabase? {
            if (instance==null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                "records"
                ).build()
            }
            return instance
        }
        fun destroyInstance() {
            instance=null
        }
    }
}