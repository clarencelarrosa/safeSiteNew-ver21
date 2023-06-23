package com.example.safesite.todo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo
    val title: String?,

    @ColumnInfo
    val description: String?,
    @ColumnInfo
    var color: Int?
): Serializable