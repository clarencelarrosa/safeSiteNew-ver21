package com.example.safesite.record

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable



@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val siteName: String,
    val ppe: String,
    val date: String,
    val total: Int
) : Serializable