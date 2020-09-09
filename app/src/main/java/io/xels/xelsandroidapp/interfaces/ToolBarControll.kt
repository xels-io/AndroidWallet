package io.xels.xelsandroidapp.interfaces

import android.content.Context
import androidx.fragment.app.FragmentActivity

interface ToolBarControll {

    fun setTitle(title: String)
    fun showDialog(showDialog: Boolean)
    fun internetCheck( context: androidx.fragment.app.FragmentActivity?): Boolean {

        var isActive: Boolean = true

        return isActive
    }

    fun loadFragmentByPosition(flag:Int)

    fun showShareBtn(show:Boolean)

    fun enableSelectedDrawer()
}