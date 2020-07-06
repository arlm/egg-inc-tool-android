package br.com.alexandremarcondes.egginc.companion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["egg_inc_id"], unique = true)])
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "egg_inc_id") val eggIncId: String
)
