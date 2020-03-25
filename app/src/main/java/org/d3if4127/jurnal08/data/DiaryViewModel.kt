package org.d3if4127.jurnal08.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import org.d3if4127.jurnal08.database.Diary
import org.d3if4127.jurnal08.database.DiaryDAO
import java.text.SimpleDateFormat
import java.util.*

class DiaryViewModel(val database: DiaryDAO, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val list_diary = database.get()

    private fun dateNow(): String {
        val formatedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val formatedTime = SimpleDateFormat("HH:mm").format(Date())
        val DateTime = "$formatedDate  $formatedTime"
        return DateTime
    }

    fun onPush(content: String){
        uiScope.launch {
            val diary = Diary(content = content, lastUpdate = dateNow())
            insert(diary)
        }
    }
    fun onDelete(id: Long) {
        uiScope.launch {
            delete(id)
        }
    }
    fun onClear() {
        uiScope.launch {
            clear()
        }
    }
    fun onUpdate(id: Long, newContent: String) {
        uiScope.launch {
            update(id, newContent)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.delete(id)
        }
    }
    private suspend fun update(id: Long, newContent: String) {
        withContext(Dispatchers.IO) {
            database.update(id = id, newContent = newContent, lastUpdate = dateNow())
        }
    }
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun insert(diary: Diary) {
        withContext(Dispatchers.IO) {
            database.insert(diary)
        }
    }

}