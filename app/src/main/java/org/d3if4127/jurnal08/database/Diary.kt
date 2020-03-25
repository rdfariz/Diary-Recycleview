package org.d3if4127.jurnal08.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Diary_table")
data class Diary (
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,
    @ColumnInfo(name = "Diary")
    var content:String,
    @ColumnInfo(name = "lastUpdate")
    var lastUpdate: String?
)