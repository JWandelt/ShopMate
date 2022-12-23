package com.example.shoppinglist2.other

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist2.R
import com.example.shoppinglist2.data.db.entities.ShoppingList
import com.example.shoppinglist2.ui.shoppinglist.ShoppingListViewModel

class ShoppingListAdapter(
    var lists : List<ShoppingList>,
    private val viewModel : ShoppingListViewModel
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>(){

    inner class ShoppingListViewHolder(listView : View) : RecyclerView.ViewHolder(listView), View.OnClickListener{
        private var normalColor: Int = Color.WHITE

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view : View){
            // Show a toast message when the item is clicked:
            val animator1 = ValueAnimator.ofObject(ArgbEvaluator(), normalColor, Color.parseColor("#FFCCCC"))
            animator1.duration = 500
            animator1.addUpdateListener { animator -> itemView.setBackgroundColor(animator.animatedValue as Int) }
            animator1.start()

            // Animate the color back to the normal color after a delay:
            val animator2 = ValueAnimator.ofObject(ArgbEvaluator(), Color.parseColor("#FFCCCC"), normalColor)
            animator2.startDelay = 500 // Set the delay to 500 milliseconds
            animator2.duration = 500
            animator2.addUpdateListener { animator -> itemView.setBackgroundColor(animator.animatedValue as Int) }
            animator2.start()
            val context = view.context
            val text = "Item clicked"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
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