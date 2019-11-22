package io.xels.xelsandroidapp.interfaces

interface OnHistoryClickListener<T> : BaseRecyclerListener {
    fun onItemClicked (item : T)
}