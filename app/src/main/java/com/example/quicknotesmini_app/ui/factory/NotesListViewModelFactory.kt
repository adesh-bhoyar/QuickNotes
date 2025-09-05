package com.example.quicknotesmini_app.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quicknotesmini_app.ui.list.NotesListViewModel

class NotesListViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            return NotesListViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

