package com.example.safesite.database

//Table in register database. This is for registering.
//This is where the registered user will be saved.

/*
The name of the table for registered user is "user_register_table"
it has five (5) columns, one of it is auto generated and primary key.
 */

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_register_table")
data class Register (
        @PrimaryKey(autoGenerate = true)
        var userId: Int,

        @ColumnInfo(name="first_name")
        var firstName: String,

        @ColumnInfo(name="last_name")
        var lastName: String,

        @ColumnInfo(name = "username")
        var username: String,

        @ColumnInfo(name="password")
        var password: String
)