package com.comporg.finalprojectv3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter2(
    private val mList: List<userPlantItem>,
    private val listener: OnItemClickListener<userPlantItem>,
    private val textListener: OnItemClickListener<userPlantItem>
) : RecyclerView.Adapter<CustomAdapter2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listmyitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userPlantItem = mList[position]
        Picasso.get().load(userPlantItem.img).into(holder.imageView)
        holder.textView.text = userPlantItem.name

        holder.itemView.setOnClickListener {
            listener.onItemClick(userPlantItem)
        }

        // Set the text view click listener
        holder.textView.setOnClickListener {
            textListener.onCancelClick(userPlantItem)
        }

        // Set the cancel button click listener
        holder.cancelButton.setOnClickListener {
            textListener.onCancelClick(userPlantItem) // Call the onCancelClick method
        }
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.plantname)
        val cancelButton: TextView = itemView.findViewById(R.id.CancelButton)

    }
}
