package com.omersungur.todoapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.omersungur.todoapp.R
import com.omersungur.todoapp.databinding.FragmentNoteListBinding
import com.omersungur.todoapp.ui.adapter.NoteListRecyclerAdapter
import com.omersungur.todoapp.ui.viewmodel.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: NoteListViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNoteList.layoutManager = LinearLayoutManager(requireContext())

        binding.fabAddItem.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_noteListFragment_to_addNoteFragment)
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            binding.rvNoteList.adapter = NoteListRecyclerAdapter(requireContext(), it, viewModel)
        }

        viewModel.newList.observe(viewLifecycleOwner) {
            binding.rvNoteList.adapter = NoteListRecyclerAdapter(requireContext(), it, viewModel)
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchNote(newText)
                return false
            }
        })
    }

    fun searchNote(query: String) {
        viewModel.searchNote(query)
    }
}