package com.example.quicknotesmini_app.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quicknotesmini_app.R
import com.example.quicknotesmini_app.data.local.Note
import com.example.quicknotesmini_app.databinding.ItemNoteBinding

class SimpleNotesAdapter(private val context: Context, private val onClick: (Long) -> Unit) :
    ListAdapter<Note, SimpleNotesAdapter.VH>(DIFF) {

    object DIFF : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    }

    inner class VH(val b: ItemNoteBinding) : RecyclerView.ViewHolder(b.root) {
        init {
            b.root.setOnClickListener { onClick(getItem(bindingAdapterPosition).id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val n = getItem(position)
        holder.b.title.text = n.title
        holder.b.subtitle.text = n.body.take(64)
        holder.b.updatedTime.text = "Updated: ${n.updatedAt.toFormattedDate()}"
        holder.b.tags.text = "Tags: ${n.tags}"
    }

    fun Long.toFormattedDate(): String {
        val date = java.util.Date(this)
        val format =
            java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
        return format.format(date)
    }

}
