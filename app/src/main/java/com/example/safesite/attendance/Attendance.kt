package com.example.safesite.attendance

class Attendance(var name: String,
                 var time: Int,
                 var unit: String,
                 var checked: Boolean = false) {

    override fun toString(): String {
        return "Attendance(name='$name', " +
                "time=$time, " +
                "unit='$unit', " +
                "checked=$checked)"
    }
}