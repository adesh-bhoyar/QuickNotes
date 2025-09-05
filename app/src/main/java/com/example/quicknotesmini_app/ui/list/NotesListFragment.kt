package com.example.quicknotesmini_app.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quicknotesmini_app.R
import com.example.quicknotesmini_app.databinding.FragmentNotesListBinding
import com.example.quicknotesmini_app.ui.common.SimpleNotesAdapter
import com.example.quicknotesmini_app.ui.factory.NotesListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val vm: NotesListViewModel by viewModels {
        NotesListViewModelFactory(requireActivity().application)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SimpleNotesAdapter(requireContext()) { id ->
            findNavController().navigate(R.id.action_to_detail)
        }
        binding.recycler.adapter = adapter
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                vm.query.value = newText ?: ""
                return true
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            vm.notes.collectLatest { adapter.submitList(it) }
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_to_detail)
        }

        enableSwipeToDelete(view, adapter)
    }

    private fun enableSwipeToDelete(parentView: View, adapter: SimpleNotesAdapter) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val note = adapter.currentList[position]

                vm.delete(note) {
                    showUndoSnackbar(parentView)
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.recycler)
    }

    private fun showUndoSnackbar(view: View) {
        Snackbar.make(view, "Note deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                vm.undoDelete()
            }
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
