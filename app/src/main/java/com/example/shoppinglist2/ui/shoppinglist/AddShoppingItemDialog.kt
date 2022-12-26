package com.example.shoppinglist2.ui.shoppinglist

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.shoppinglist2.R
import com.example.shoppinglist2.data.db.entities.ShoppingItem

class AddShoppingItemDialog(listId : Int, context : Context, var addDialogListener: AddDialogListener) : AppCompatDialog(context) {

    private var idOfList = listId
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_shoppingitem)

        findViewById<TextView>(R.id.tvAdd)?.setOnClickListener{
            val name = findViewById<EditText>(R.id.etName)?.text.toString()
            val amount = findViewById<EditText>(R.id.etAmount)?.text.toString()

            if(name.isEmpty() || amount.isEmpty()){
                Toast.makeText(context, "Please enter all the information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = ShoppingItem(idOfList, name, amount.toInt(), false)
            addDialogListener.onAddButtonClicked(item)
            dismiss()
        }

        findViewById<TextView>(R.id.tvCancel)?.setOnClickListener {
            cancel()
        }
    }
}