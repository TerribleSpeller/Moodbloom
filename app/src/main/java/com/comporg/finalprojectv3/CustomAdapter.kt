package com.comporg.finalprojectv3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(
    private val mList: List<plantItem>,
    private val listener: OnItemClickListener<plantItem>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plantItem = mList[position]
        Picasso.get().load(plantItem.Img).into(holder.imageView)
        holder.textView.text = plantItem.Name
        holder.textView2.text = plantItem.MoistMax.toString()
        holder.textView3.text = plantItem.MoistMin.toString()

        holder.itemView.setOnClickListener {
            listener.onItemClick(plantItem)
        }
    }

    override fun getItemCount(): Int = mList.size

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.plantname)
        val textView2: TextView = itemView.findViewById(R.id.moistmaxdisplay)
        val textView3: TextView = itemView.findViewById(R.id.moistmindisplay)
    }
}
