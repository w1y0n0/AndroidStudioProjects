package com.labsi.myprodipnc

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.labsi.myprodipnc.databinding.ItemCardviewProdiBinding

class ListProdiAdapter(private val listProdi: ArrayList<Prodi>) : RecyclerView.Adapter<ListProdiAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Prodi)
    }

    class ListViewHolder(var binding: ItemCardviewProdiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        var binding = ItemCardviewProdiBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listProdi.size
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name,jenjang,tglBerdiri,akreditasi,skSelenggara,tglSkSelenggara,infoUmum,ilmu,kompetensi,photo) = listProdi[position]
        holder.binding.civFoto.setImageResource(photo)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDesc.text = infoUmum
        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listProdi[holder.adapterPosition])
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("EXTRA_PRODI", listProdi[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}