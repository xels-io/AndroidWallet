package io.xels.xelsandroidapp.adapter

import androidx.fragment.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class WordGridViewAdapter(var word: List<String>?, var context: androidx.fragment.app.FragmentActivity?) : BaseAdapter() {

    var item: Int = 0;

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val dummyTextView = TextView(context)

        item = position + 1


        dummyTextView.setText(
            item.toString().plus(". ").plus(word?.get(position))
        )
        return dummyTextView
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return word!!.size
    }


}