package br.com.mouzinho.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun jsonToObject(data: String): List<String> {
        if (data.isEmpty())
            return listOf()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun objectToJson(data: List<String>): String {
        return gson.toJson(data)
    }
}