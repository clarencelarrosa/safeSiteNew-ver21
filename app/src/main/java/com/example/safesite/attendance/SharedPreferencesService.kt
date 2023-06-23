package com.example.safesite.attendance

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson

class SharedPreferencesService(appContext: Context) {
    private val preferences: SharedPreferences =
        appContext.getSharedPreferences("attendance_list",
            Context.MODE_PRIVATE)

    fun getDateLists(key: String, mClass: Class<*>): ArrayList<Any> {
        val gson = Gson()
        val objectStrings = ArrayList(listOf(*TextUtils.split(preferences.getString(key, ""),
            "‚‗‚")))
        val objects = ArrayList<Any>()
        for (jObjString in objectStrings) {
            val value = gson.fromJson(jObjString, mClass)
            objects.add(value)
        }
        return objects
    }

    fun saveDateLists(key: String, objectArray: ArrayList<Any>) {
        val gson = Gson()
        val objectStrings = ArrayList<String>()
        for (objects in objectArray) {
            objectStrings.add(gson.toJson(objects))
        }
        val myStringList = objectStrings.toTypedArray()
        preferences.edit().putString(key, TextUtils.join("‚‗‚",
            myStringList)).apply()
    }
}