package com.example.shoppinglist2.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist2.data.db.ShoppingDatabase
import com.example.shoppinglist2.data.repositories.ShoppingListRepository
import com.example.shoppinglist2.databinding.FragmentListsBinding

class ListsFragment : Fragment() {

    private var _binding: FragmentListsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val database = ShoppingDatabase(requireContext())
        val repository = ShoppingListRepository(database)
        val factory = ShoppingListViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(ShoppingListViewModel::class.java)
        val adapter = ShoppingListAdapter(listOf(), viewModel)

        _binding = FragmentListsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv = binding.rvShoppingItems

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewModel.getAllLists().observe(viewLifecycleOwner) {
            adapter.lists = it
            adapter.notifyDataSetChanged()
        }

        return root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setTitle("Lists")
        }
        Toast.makeText(context, "smt", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}