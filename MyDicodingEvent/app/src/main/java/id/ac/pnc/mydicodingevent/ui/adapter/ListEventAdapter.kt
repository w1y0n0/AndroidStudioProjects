package id.ac.pnc.mydicodingevent.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import id.ac.pnc.mydicodingevent.data.remote.response.ListEventsItem
import id.ac.pnc.mydicodingevent.databinding.HorizontalRowEventBinding
import id.ac.pnc.mydicodingevent.databinding.ItemRowEventBinding
import id.ac.pnc.mydicodingevent.ui.detail.DetailActivity
import id.ac.pnc.mydicodingevent.utils.loadImage

class ListEventAdapter(
    private val listEvent: List<ListEventsItem>,
    private val horizontal: Boolean = false,
    private val disableEllipsize: Boolean = false
) :
    RecyclerView.Adapter<ListEventAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = (if (horizontal) HorizontalRowEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ) else ItemRowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listEvent[position]

        if (horizontal) {
            val binding = holder.binding as HorizontalRowEventBinding
            binding.cardTitle.text = data.name
//            binding.cardSummary.text = data.summary
            binding.cardCover.loadImage(data.imageLogo)

            // Jika disableEllipsize = true, hapus batasan teks
            if (disableEllipsize) {
                binding.cardTitle.ellipsize = null
                binding.cardTitle.maxLines = Integer.MAX_VALUE
            }
        } else {
            val binding = holder.binding as ItemRowEventBinding
            binding.cardTitle.text = data.name
            binding.cardSummary.text = data.summary
            binding.cardCover.loadImage(data.imageLogo)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.Companion.EXTRA_EVENT_ID, data.id)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = listEvent.size
}