package id.ac.pnc.mydicodingevent.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.set(spacing, spacing, spacing, spacing) // Set jarak di semua sisi
    }
}

class HorizontalSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = spacing / 2
        outRect.right = spacing / 2

        // Tambahkan padding kiri untuk item pertama
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spacing
        }

        // Tambahkan padding kanan untuk item terakhir
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = spacing
        }
    }
}