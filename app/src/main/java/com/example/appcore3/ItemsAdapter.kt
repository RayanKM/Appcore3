package com.example.appcore3

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter (private val context: Context, private var items: List<BooksDataModel>) :
    RecyclerView.Adapter<UserViewHolder>() {

    private lateinit var mListenerA: onItemClickListener
    private var option: Int = 1

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setItems(newItems: List<BooksDataModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun setOption(options: Int) {
        option = options
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: Any){
        mListenerA = listener as onItemClickListener
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false),mListenerA
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val item = items[position]
        holder.Title.text = item.title
        holder.Location.text = item.location
        holder.Time.text = item.time

        if (option == 1){
            // Set the visibility of the icon for all cases
            if (item.location == "Online") {
                holder.Icon.visibility = View.GONE
            } else {
                holder.Icon.visibility = View.VISIBLE
            }
        }else{
            holder.Icon.visibility = View.GONE
            val type = item.title

            when (type) {
                "UN Students" -> holder.Card.setCardBackgroundColor(Color.parseColor("#3498db")) // Replace with your desired color code
                "Xsports" -> holder.Card.setCardBackgroundColor(Color.parseColor("#2ecc71"))
                "Apolitical" -> holder.Card.setCardBackgroundColor(Color.parseColor("#e74c3c"))
                "Football" -> holder.Card.setCardBackgroundColor(Color.parseColor("#9b59b6"))
                else -> "Invalid day" // This is the default case
            }
        }
    }
}

class UserViewHolder(itemView: View, listener: ItemsAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

    val Title: TextView = itemView.findViewById(R.id.title)
    val Icon: ImageView = itemView.findViewById(R.id.icon)
    val Location: TextView = itemView.findViewById(R.id.genre)
    val Time : TextView = itemView.findViewById(R.id.type)
    val Card : CardView = itemView.findViewById(R.id.card)

    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}
