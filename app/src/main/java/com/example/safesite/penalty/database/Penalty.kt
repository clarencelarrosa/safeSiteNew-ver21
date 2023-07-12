package com.example.safesite.penalty.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//tables in penalties. It has the date of record, total amount of penalty fee, the penalty description
@Entity(tableName = "penalty_table")
data class Penalty(

    @PrimaryKey(autoGenerate = true)
    var penaltyId: Int,

    @ColumnInfo(name = "penaltyDate")
    var penaltyDate: String,

    @ColumnInfo(name = "penaltyAmount")
    var penaltyAmount: String,

    @ColumnInfo(name = "penaltyDesc")
    var penaltyDesc:String,
)