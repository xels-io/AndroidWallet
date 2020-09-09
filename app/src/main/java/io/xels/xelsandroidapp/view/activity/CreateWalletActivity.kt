package io.xels.xelsandroidapp.view.activity

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.view.fragment.CreateWalletComplete
import io.xels.xelsandroidapp.view.fragment.CreateWalletFragment
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.ulits.Utils

class CreateWalletActivity : androidx.fragment.app.FragmentActivity(), ToolBarControll {
    override fun enableSelectedDrawer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadFragmentByPosition(flag: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showShareBtn(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTitle(title: String) {
    }


    var fragment: androidx.fragment.app.Fragment? = null
    private var progress: KProgressHUD? = null
    var typeNetwork: IntArray = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_wallet)
        progress = KProgressHUD(this@CreateWalletActivity)

        fragment = CreateWalletFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment as CreateWalletFragment, "CreateWalletFragment").commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        Log.e("count",""+supportFragmentManager.backStackEntryCount)
        if (fragment is CreateWalletComplete) {
            finish()
        }
    }

    override fun showDialog(showDialog: Boolean) {
        if (showDialog) {

            progress?.show()

        } else {
            progress?.dismiss()

        }
    }

    override fun internetCheck(context: androidx.fragment.app.FragmentActivity?): Boolean {
        var isActive = Utils.isNetworkAvailable(this@CreateWalletActivity, typeNetwork)


        if (!isActive) {
            Toast.makeText(context, "Internet not available", Toast.LENGTH_SHORT).show()
        }


        return isActive
    }

}
