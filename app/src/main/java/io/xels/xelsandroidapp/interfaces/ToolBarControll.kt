package io.xels.xelsandroidapp.interfaces

import android.content.Context
import android.support.v4.app.FragmentActivity

interface ToolBarControll {

    fun setTitle(title: String)
    fun showDialog(showDialog: Boolean)
    fun internetCheck( context: FragmentActivity?): Boolean {

        var isActive: Boolean = true

        return isActive
    }

    fun loadFragmentByPosition(flag:Int)

    fun showShareBtn(show:Boolean)

    fun enableSelectedDrawer()
}