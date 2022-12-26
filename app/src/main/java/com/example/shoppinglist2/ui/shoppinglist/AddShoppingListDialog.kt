package com.example.shoppinglist2.ui.shoppinglist

import androidx.appcompat.app.AppCompatDialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.shoppinglist2.R
import com.example.shoppinglist2.data.db.entities.ShoppingItem
import com.example.shoppinglist2.data.db.entities.ShoppingList

class AddShoppingListDialog(context : Context, var addListDialogListener : AddListDialogListener) : AppCompatDialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_list)

        findViewById<TextView>(R.id.tvAdd)?.setOnClickListener{
            val name = findViewById<EditText>(R.id.etName)?.text.toString()

            if(name.isEmpty()){
                Toast.makeText(context, "Please enter all the information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val list = ShoppingList(name)
            addListDialogListener.onAddButtonClicked(list)
            dismiss()
        }

        findViewById<TextView>(R.id.tvCancel)?.setOnClickListener {
            cancel()
        }
    }
}