package com.example.shoppinglist2.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist2.data.db.ShoppingDatabase
import com.example.shoppinglist2.data.db.entities.ShoppingList
import com.example.shoppinglist2.data.repositories.ShoppingListRepository
import com.example.shoppinglist2.databinding.FragmentListsBinding
import com.example.shoppinglist2.other.ShoppingListAdapter
import com.example.shoppinglist2.ui.lists.ShoppingListViewModelFactory

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
//        val listsViewModel =
//            ViewModelProvider(this).get(ListsViewModel::class.java)


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}