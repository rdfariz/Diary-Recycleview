package org.d3if4127.jurnal08.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiaryDAO {
    @Insert
    fun insert(diary: Diary)

    @Query("DELETE FROM Diary_table")
    fun clear()

    @Query("UPDATE Diary_table SET Diary=:newContent, lastUpdate=:lastUpdate WHERE id = :id")
    fun update(newContent: String, id: Long, lastUpdate: String?)

    @Query("SELECT * FROM Diary_table ORDER BY id DESC")
    fun get(): LiveData<List<Diary>>

    @Query("DELETE FROM Diary_table WHERE id=:id")
    fun delete(id: Long)
}