package com.labsi.myrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.labsi.myrecyclerview.databinding.ItemRowHeroBinding

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }

//    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
//        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
//        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
//    }
    class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
//        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_hero, viewGroup, false)
//        return ListViewHolder(view)
        val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listHero.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name,description,photo) = listHero[position]
        holder.binding.imgItemPhoto.setImageResource(photo)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDescription.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHero[holder.adapterPosition])
        }
    }
}
