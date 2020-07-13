package io.xels.xelsandroidapp.view.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.event_bus_object.LogoutObject
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.model.AddressGetModel
import io.xels.xelsandroidapp.model.CallAddressModel
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import io.xels.xelsandroidapp.ulits.Utils.isNetworkAvailable
import io.xels.xelsandroidapp.view.fragment.*
import kotlinx.android.synthetic.main.fragment_recieve.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BaseActivity : FragmentActivity(), ToolBarControll, View.OnClickListener,

    NavigationView.OnNavigationItemSelectedListener {
    override fun enableSelectedDrawer() {
        val fragment1 = supportFragmentManager.findFragmentById(R.id.frameLayout)
        if (fragment1 is DashBoardFragment) {
            navigationView!!.menu.getItem(0).setChecked(true)
        } else if (fragment1 is HistoryFragment) {
            navigationView!!.menu.getItem(1).setChecked(true)
        } else if (fragment1 is ReceiveFragment) {
            navigationView!!.menu.getItem(2).setChecked(true)
        }

    }

    override fun loadFragmentByPosition(flag: Int) {
        when (flag) {
            1 -> {
                fragment = TransactionHistoryFragment()
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frameLayout,
                        fragment as TransactionHistoryFragment,
                        "HistoryFragment"
                    )
                    .addToBackStack("TransactionHistoryFragment").commit()

                navigationView!!.menu.getItem(flag).setChecked(true)
            }
            2 -> {
                fragment = ReceiveFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as ReceiveFragment, "ReceiveFragment")
                    .addToBackStack("ReceiveFragment").commit()

                navigationView!!.menu.getItem(flag).setChecked(true)
            }
            3 -> {
                fragment = SendFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as SendFragment, "SendFragment")
                    .addToBackStack("SendFragment").commit()

                navigationView!!.menu.getItem(flag).setChecked(true)
            }
        }
    }

    override fun showShareBtn(show: Boolean) {
        if (show) {
            shareBtn?.visibility = View.VISIBLE
        } else {
            shareBtn?.visibility = View.GONE

        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val id = p0.itemId
        if (id == R.id.latestTransaction) {

            if (internetCheck(this@BaseActivity)) {

                fragment = TransactionHistoryFragment()
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.frameLayout,
                        fragment as TransactionHistoryFragment,
                        "HistoryFragment"
                    )
                    .addToBackStack("TransactionHistoryFragment").commit()
            }


        } else if (id == R.id.receive) {

            if (internetCheck(this@BaseActivity)) {
                fragment = ReceiveFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as ReceiveFragment, "ReceiveFragment")
                    .addToBackStack("ReceiveFragment").commit()
            }


        } else if (id == R.id.send) {

            if (internetCheck(this@BaseActivity)) {
                fragment = SendFragment()
                bundle = Bundle()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as SendFragment, "SendFragment")
                    .addToBackStack("SendFragment").commit()
            }

        } else if (id == R.id.logout) {
            val alertDeleteGroupFragment = LogoutAlertDialog()
            alertDeleteGroupFragment.show(supportFragmentManager, null)
            /*Utils.logout(this@BaseActivity)
            finish()*/
        } else if (id == R.id.dashBoard) {

            if (internetCheck(this@BaseActivity)) {

                fragment = DashBoardFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as DashBoardFragment, "DashBoardFragment")
                    .addToBackStack("DashBoardFragment").commit()
            }
        } else if (id == R.id.stacked) {

            if (internetCheck(this@BaseActivity)) {

                fragment = StackedFragment()

                var b = Bundle();
                b.putInt("type", 0)
                (fragment as StackedFragment).arguments = b

                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as StackedFragment, "StackedFragment")
                    .addToBackStack("StackedFragment").commit()
            }


        } else if (id == R.id.pow_reward) {
            if (internetCheck(this@BaseActivity)) {

                fragment = StackedFragment()

                var b = Bundle()
                b.putInt("type", 1)

                (fragment as StackedFragment).arguments = b
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment as StackedFragment, "StackedFragment")
                    .addToBackStack("StackedFragment").commit()
            }
        }

        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun logout(logoutObject: LogoutObject) {
        Utils.logout(this@BaseActivity)
        finish()
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.drawerBtn -> {
                drawer!!.openDrawer(Gravity.START)
            }
            R.id.shareBtn -> {
                EventBus.getDefault().post(CallAddressModel())
            }
        }
    }

    override fun showDialog(showDialog: Boolean) {
        if (showDialog) {

            progress?.show()

        } else {
            progress?.dismiss()

        }
    }


    var typeNetwork: IntArray =
        intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
    var toolBar: Toolbar? = null
    var titleTxt: TextView? = null
    var drawerBtn: Button? = null
    var drawer: DrawerLayout? = null
    var bundle: Bundle? = null
    var navigationView: NavigationView? = null
    private var progress: KProgressHUD? = null
    override fun setTitle(title: String) {
        titleTxt?.text = title
    }


    override fun internetCheck(context: FragmentActivity?): Boolean {

        var isActive = isNetworkAvailable(this@BaseActivity, typeNetwork)
        if (!isActive) {
            Utils.showAlertDialg(this@BaseActivity)
        }
        return isActive
    }


    var fragment: Fragment? = null
    var shareBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        progress = KProgressHUD(this@BaseActivity)
        Utils.showDialog(this@BaseActivity)

        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView!!.setNavigationItemSelectedListener(this)

        var headerView: View = navigationView!!.inflateHeaderView(R.layout.nav_header_base)

        var headeerTitle: TextView = headerView.findViewById(R.id.headerTitle)

        headeerTitle.text = PreferenceManager.getString(AppConstance.walletName)

        toolBar = findViewById(R.id.toolbar)
        titleTxt = toolBar?.findViewById(R.id.text_screen_title)
        drawerBtn = toolBar?.findViewById(R.id.drawerBtn)
        shareBtn = toolBar?.findViewById(R.id.shareBtn)
        shareBtn?.setOnClickListener(this)
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawerBtn?.setOnClickListener(this)
        titleTxt?.text = "Dashboard"

        Log.d("wallet name:", PreferenceManager.getString(AppConstance.walletName))
        fragment = DashBoardFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment as DashBoardFragment, "DashBoardFragment")
            .addToBackStack("DashBoardFragment").commit()

        navigationView!!.setItemIconTintList(null)
        //navigationView.menu.getItem()
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        }

        if (!drawer!!.isDrawerOpen(GravityCompat.START)) {
            if (supportFragmentManager.backStackEntryCount == 1 || supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else {
                val fragment1 = supportFragmentManager.findFragmentById(R.id.frameLayout)

                if (fragment1 is HistoryFragment || fragment1 is ReceiveFragment || fragment1 is SendFragment || fragment1 is ShowAllAddressFragment) {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    fragment = DashBoardFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            fragment as DashBoardFragment,
                            "DashBoardFragment"
                        )
                        .addToBackStack("DashBoardFragment").commit()
                    navigationView!!.menu.getItem(0).setChecked(true)
                } else if (fragment1 is DashBoardFragment) {
                    finish()
                } else {
                    supportFragmentManager.popBackStack()
                }

                //supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}