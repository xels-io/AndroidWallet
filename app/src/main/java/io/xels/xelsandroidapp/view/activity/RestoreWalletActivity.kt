package io.xels.xelsandroidapp.view.activity

import androidx.lifecycle.ViewModelProviders
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.*
import com.kaopiz.kprogresshud.KProgressHUD
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.response_model.LoadApiResponseModel
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.Utils
import io.xels.xelsandroidapp.view_model.RestoreViewModel
import retrofit2.Call
import retrofit2.Response
import java.util.*

class RestoreWalletActivity : androidx.fragment.app.FragmentActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.restoreBtn -> {
                if (name?.text.toString().isEmpty()) {
                    makeToast(resources.getString(R.string.text_enter_wallet_name))
                } else if (!Utils.checkWalletNameValidity(name?.text.toString().trim())) {
                    makeToast(resources.getString(R.string.text_wallet_name_mismatch))
                } else if (name?.text.toString().trim().length < 1 || name?.text.toString().trim().length > 24) {
                    makeToast(resources.getString(R.string.text_wallet_name_length_checker))
                } else if (name?.text.toString().equals(password?.text.toString())) {
                    makeToast(resources.getString(R.string.text_wallet_name_password_same_warning))
                } else if (date?.text.toString().isEmpty()) {
                    makeToast(resources.getString(R.string.text_enter_date))
                } else if (word?.text.toString().isEmpty()) {
                    makeToast(resources.getString(R.string.text_enter_word))
                } else if (!Utils.checkPassword(password?.text.toString())) {
                    makeToast(resources.getString(R.string.text_password_type_mismatch))
                } else if (mPassphraseTxtView?.text.toString().trim().isEmpty()) {
                    makeToast(resources.getString(R.string.text_enter_passphrase))
                } else {

                    if (Utils.isNetworkAvailable(this@RestoreWalletActivity, typeNetwork)) {
                        //restoreWallet(apiInterface)
                        restoreWallet()
                    } else {
                        Utils.showAlertDialg(this@RestoreWalletActivity)
                    }
                }
            }

            R.id.date -> {
                showCalendar()
            }
            R.id.layout -> {
                Utils.hideKeyBoard(this@RestoreWalletActivity)
            }
        }
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showCalendar() {
        var now: Calendar = Calendar.getInstance()
        val dialog = DatePickerFragmentDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
            month = monthOfYear + 1;
            if (month < 10) {
                date?.text = year.toString() + "-0" + month + "-" + dayOfMonth
            } else {
                date?.text = year.toString() + "-" + month + "-" + dayOfMonth
            }
        })
        dialog.setMaxDate(System.currentTimeMillis())

        dialog.show(supportFragmentManager, "tag")
        dialog.setAccentColor("#62B04F")
    }

    var name: EditText? = null
    var date: TextView? = null
    var word: EditText? = null
    var password: EditText? = null
    var mPassphraseTxtView: EditText? = null
    var restoreBtn: Button? = null
    var layout: LinearLayout? = null
    var apiInterface: ApiInterface? = null
    var month: Int = 0
    var typeNetwork: IntArray = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
    private var progress: KProgressHUD? = null
    private var restoreViewModel: RestoreViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_wallet)
        restoreViewModel = ViewModelProviders.of(this).get(RestoreViewModel::class.java)
        //apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        val title: TextView = toolbar.findViewById(R.id.text_screen_title) as TextView
        title.setText(R.string.restore_a_wallet)
        progress = KProgressHUD(this)
        Utils.showDialog(this)
        init()
    }

    private fun init() {
        name = findViewById(R.id.walletName)
        date = findViewById(R.id.date)
        word = findViewById(R.id.words)
        password = findViewById(R.id.password)
        mPassphraseTxtView = findViewById(R.id.passphraseTxtView)
        restoreBtn = findViewById(R.id.restoreBtn)
        layout = findViewById(R.id.layout)
        restoreBtn?.setOnClickListener(this)
        layout?.setOnClickListener(this)
        date?.setOnClickListener(this)
    }

    private fun restoreWallet() {
        progress?.show()
        restoreViewModel?.getRestoreWallet(
            date?.text.toString(),
            word?.text.toString(),
            name?.text.toString(),
            mPassphraseTxtView?.text.toString(),
            password?.text.toString()
        )
            ?.observe(this, androidx.lifecycle.Observer { restoreWalletResponse ->
                if (restoreWalletResponse != null) {
                    progress?.dismiss()
                    finish()
                    makeToast("Wallet successfully recovered")
                } else {
                    progress?.dismiss()
                }
            })
    }


    /*private fun restoreWallet(apiInterface: ApiInterface?) {
        progress?.show()
        apiInterface?.restoreWalletApi(
            AppConstance.recoverWallet,
            date?.text.toString(),
            word?.text.toString(),
            name?.text.toString(), mPassphraseTxtView?.text.toString()
        )?.enqueue(object : retrofit2.Callback<LoadApiResponseModel?> {
            override fun onFailure(call: Call<LoadApiResponseModel?>, t: Throwable) {
                println(t.printStackTrace())
                makeToast("Something went wrong. please try later")
                progress?.dismiss()
            }

            override fun onResponse(
                call: Call<LoadApiResponseModel?>,
                response: Response<LoadApiResponseModel?>
            ) {
                progress?.dismiss()
                if (response.isSuccessful) {
                    finish()
                    makeToast("Wallet successfully recovered")

                } else {
                    Utils.handleErrorResponse(response, this@RestoreWalletActivity, response.code())
                }
            }
        })
    }*/

}