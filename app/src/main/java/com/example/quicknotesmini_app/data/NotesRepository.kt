package com.example.quicknotesmini_app.data

import com.example.quicknotesmini_app.data.local.Note
import com.example.quicknotesmini_app.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepository(private val dao: NoteDao) {
    fun observeNotes(query: String, tagFilter: String?): Flow<List<Note>> =
        dao.observeAll().map { list ->
            list.filter { n ->
                val q = query.trim().lowercase()
                val okQ = q.isEmpty() || n.title.lowercase().contains(q) ||
                        n.body.lowercase().contains(q) || n.tags.lowercase().contains(q)
                val okTag =
                    tagFilter.isNullOrBlank() || n.tags.split(",").map { it.trim().lowercase() }
                        .contains(tagFilter.lowercase())
                okQ && okTag
            }
        }

    suspend fun get(id: Long) = dao.getById(id)
    suspend fun upsert(note: Note) = dao.upsert(note)
    suspend fun delete(note: Note) = dao.delete(note)
}
