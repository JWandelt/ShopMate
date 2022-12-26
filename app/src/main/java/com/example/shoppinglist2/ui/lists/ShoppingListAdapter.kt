package com.example.shoppinglist2.ui.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist2.R
import com.example.shoppinglist2.data.db.entities.ShoppingList
import com.example.shoppinglist2.ui.items.ItemsFragment

class ShoppingListAdapter(
    var lists : List<ShoppingList>,
    private val viewModel : ShoppingListViewModel
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>(){

    inner class ShoppingListViewHolder(listView : View) : RecyclerView.ViewHolder(listView), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view : View){
            val positionOfRecyclerVieElement = adapterPosition
            val listId = positionOfRecyclerVieElement + 1
            val fragmentTransaction = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
            val fragment = ItemsFragment(listId)
            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_list, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val curList = lists[position]

        holder.itemView.findViewById<TextView>(R.id.tv_listName).text = curList.name

        holder.itemView.findViewById<ImageView>(R.id.iv_delete).setOnClickListener {
            viewModel.delete(curList)
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }
}