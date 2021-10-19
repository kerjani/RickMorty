package com.kernacs.rickmorty.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class TypeConverters {
    @TypeConverter
    fun stringToDate(string: String): Date = string.isoStringToDate()

    @TypeConverter
    fun dateToString(date: Date): String = date.toIsoString()

    @TypeConverter
    fun restoreStringList(listOfString: String): List<String>? {
        return Gson().fromJson(listOfString, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun saveStringList(listOfString: List<String?>?): String? {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun restoreIntList(listOfInt: String): List<Int>? {
        return Gson().fromJson(listOfInt, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun saveIntList(listOfInt: List<Int?>?): String? {
        return Gson().toJson(listOfInt)
    }
}