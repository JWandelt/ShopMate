package com.example.shoppinglist2.ui.lists

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist2.data.db.ShoppingDatabase
import com.example.shoppinglist2.data.db.entities.ShoppingItem
import com.example.shoppinglist2.data.db.entities.ShoppingList
import com.example.shoppinglist2.data.repositories.ShoppingListRepository
import com.example.shoppinglist2.databinding.FragmentListsBinding
import com.example.shoppinglist2.ui.shoppinglist.AddDialogListener
import com.example.shoppinglist2.ui.shoppinglist.AddListDialogListener
import com.example.shoppinglist2.ui.shoppinglist.AddShoppingItemDialog
import com.example.shoppinglist2.ui.shoppinglist.AddShoppingListDialog
import java.util.*

class ListsFragment : Fragment() {
    private val RQ_SPEECH_REC = 102

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
        val fab = binding.fabAddList
        val fabVoice = binding.fabAddListVoice

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewModel.getAllLists().observe(viewLifecycleOwner) {
            adapter.lists = it
            adapter.notifyDataSetChanged()
        }

        fab.setOnClickListener {
            AddShoppingListDialog(requireContext(),
                object : AddListDialogListener {
                    override fun onAddButtonClicked(list: ShoppingList) {
                        viewModel.upsert(list)
                    }
                }).show()
        }

        fabVoice.setOnClickListener {
            askSpeechInput()
            if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
            }

        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val database = ShoppingDatabase(requireContext())
        val repository = ShoppingListRepository(database)
        val factory = ShoppingListViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(requireActivity(), factory).get(ShoppingListViewModel::class.java)
        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            var resultString = result.toString().replace("[", "")
            resultString = resultString.replace("]", "")
            Toast.makeText(requireContext(), resultString, Toast.LENGTH_SHORT).show()
            var list = ShoppingList(resultString)
            viewModel.upsert(list)
        }
    }

    private fun askSpeechInput(){
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!")
            startActivityForResult(i, RQ_SPEECH_REC)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}