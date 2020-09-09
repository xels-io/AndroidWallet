package io.xels.xelsandroidapp.interfaces

import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder <T,L : BaseRecyclerListener>(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: T, listener: L?)
}