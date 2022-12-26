package com.example.shoppinglist2.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist2.data.db.ShoppingDatabase
import com.example.shoppinglist2.data.repositories.ShoppingListRepository
import com.example.shoppinglist2.data.repositories.ShoppingRepository
import com.example.shoppinglist2.databinding.FragmentItemsBinding
import com.example.shoppinglist2.other.ShoppingItemAdapter
import com.example.shoppinglist2.other.ShoppingListAdapter
import com.example.shoppinglist2.ui.lists.ShoppingListViewModelFactory

class ItemsFragment : Fragment() {

    private var _binding: FragmentItemsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val database = ShoppingDatabase(requireContext())

        val repository = ShoppingRepository(database)

        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv = binding.rvShoppingItems

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewModel.getAllShoppingItems().observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}