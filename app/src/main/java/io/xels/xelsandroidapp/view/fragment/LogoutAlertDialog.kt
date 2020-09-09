package io.xels.xelsandroidapp.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.event_bus_object.LogoutObject
import org.greenrobot.eventbus.EventBus

class LogoutAlertDialog : androidx.fragment.app.DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setCancelable(false)
            .setMessage("Are you sure about Logout?")
            .setPositiveButton(resources.getString(R.string.text_ok)) { dialog, which ->
                EventBus.getDefault().post(LogoutObject(true))
                dismiss()

            }.create()

    }

}