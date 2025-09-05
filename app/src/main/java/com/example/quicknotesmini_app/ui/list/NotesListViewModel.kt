package com.example.quicknotesmini_app.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotesmini_app.data.NotesRepository
import com.example.quicknotesmini_app.data.local.Note
import com.example.quicknotesmini_app.data.local.NotesDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class NotesListViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = NotesRepository(NotesDb.get(app).noteDao())
    private var recentlyDeletedNote: Note? = null

    val query = MutableStateFlow("")
    val tag = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes = query.flatMapLatest { q ->
        repo.observeNotes(q, tag.value)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun delete(note: Note, onDeleted: () -> Unit) {
        viewModelScope.launch {
            recentlyDeletedNote = note
            repo.delete(note)
            onDeleted()
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            recentlyDeletedNote?.let {
                repo.upsert(it)
                recentlyDeletedNote = null
            }
        }
    }
}
