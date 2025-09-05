package com.example.quicknotesmini_app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quicknotesmini_app.databinding.FragmentNoteDetailBinding

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<NoteDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveBtn.setOnClickListener {
            val title = binding.title.text?.toString().orEmpty().trim()
            val body = binding.body.text?.toString().orEmpty()
            val tags = binding.tags.text?.toString().orEmpty()
            if (title.isBlank()) {
                Toast.makeText(requireContext(), "Title required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            vm.save(null, title, body, tags) {
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
