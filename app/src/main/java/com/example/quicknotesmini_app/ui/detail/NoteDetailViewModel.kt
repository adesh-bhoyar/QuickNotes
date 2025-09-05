package com.example.quicknotesmini_app.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotesmini_app.data.NotesRepository
import com.example.quicknotesmini_app.data.local.Note
import com.example.quicknotesmini_app.data.local.NotesDb
import kotlinx.coroutines.launch

class NoteDetailViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = NotesRepository(NotesDb.get(app).noteDao())

    fun save(id: Long?, title: String, body: String, tags: String, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val note = if (id == null || id == 0L) {
                Note(
                    title = title,
                    body = body,
                    tags = tags.trim(),
                    createdAt = now,
                    updatedAt = now
                )
            } else {
                val existing = repo.get(id) ?: Note(id = id, title = title)
                existing.copy(title = title, body = body, tags = tags.trim(), updatedAt = now)
            }
            val newId = repo.upsert(note)
            onSaved(newId)
        }
    }
}
