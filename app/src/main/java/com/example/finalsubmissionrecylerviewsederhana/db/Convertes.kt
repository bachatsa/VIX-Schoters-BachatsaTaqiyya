package com.example.finalsubmissionrecylerviewsederhana.db

import androidx.room.TypeConverter
import com.example.finalsubmissionrecylerviewsederhana.model.Source

class Convertes {
    @TypeConverter
    fun fromSource(source: Source) : String? {
        return source.name
    }
    @TypeConverter
    fun toSource(name: String) : Source? {
        return Source(name, name)
    }
}