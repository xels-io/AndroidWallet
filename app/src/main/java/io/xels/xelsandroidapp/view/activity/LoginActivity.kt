package io.xels.xelsandroidapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import kotlinx.android.synthetic.main.activity_login.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.xels.xelsandroidapp.view_model.LoginViewModel


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var TAG = "LoginActivity"
    private var loginViewModel: LoginViewModel? = null
    private var progress: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
       /* PreferenceManager.updateValue(AppConstance.baseUrl, AppConstance.BASE_URL)*/
        progress = KProgressHUD(this)
        Utils.showDialog(this)
        init()
    }

    private fun init() {
        text_screen_title.setText(resources.getString(R.string.login))
        btn_create_wallet.setOnClickListener(this)
        settingBtn.setOnClickListener(this)
        decryptBtn.setOnClickListener(this)
        restoreWalletBtn.setOnClickListener(this)
        layout.setOnClickListener(this)
        container.setOnClickListener(this)
    }

    private fun gotoDashBoard() {
        val intent = Intent(this, BaseActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login() {
        progress?.show()
        loginViewModel?.getLoginResponse(walletEditTxt.text.toString(), passwordEditTxt.text.toString())
            ?.observe(this, Observer { loginResponse ->
                if (loginResponse != null) {
                    PreferenceManager.updateValue(AppConstance.walletName, walletEditTxt.text.toString())
                    PreferenceManager.updateValue(AppConstance.isLogin, true)
                    PreferenceManager.updateValue(AppConstance.password, passwordEditTxt.text.toString())
                    gotoDashBoard()
                    progress?.dismiss()

                } else {
                    progress?.dismiss()
                    println("try later")
                }
            })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.layout -> {
                Utils.hideKeyBoard(this@LoginActivity)
            }
            R.id.container -> {
                Utils.hideKeyBoard(this@LoginActivity)
            }
            R.id.settingBtn -> {
                val intent = Intent(this, UrlChangeActivity::class.java)
                startActivity(intent)
            }

            R.id.decryptBtn ->
                if (walletEditTxt.text.toString().isEmpty()) {
                    Toast.makeText(this, resources.getString(R.string.wallet_name_missing), Toast.LENGTH_LONG).show()

                } else if (passwordEditTxt.text.toString().isEmpty()) {
                    Toast.makeText(this, resources.getString(R.string.password_name_missing), Toast.LENGTH_LONG).show()

                } else if (!Utils.isNetworkAvailable(this@LoginActivity, AppConstance.typeNetwork)) {
                    Utils.showAlertDialg(this@LoginActivity)

                } else {
                    login()
                }

            R.id.restoreWalletBtn -> {
                val intent = Intent(this@LoginActivity, RestoreWalletActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_create_wallet -> {
                val intent = Intent(this@LoginActivity, CreateWalletActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(PreferenceManager.getBool("first_run", true)){
            PreferenceManager.updateValue("first_run", false)
            PreferenceManager.updateValue(AppConstance.baseUrl, AppConstance.BASE_URL)
        }
        //Log.e(TAG, "Url: +" + PreferenceManager.getString(AppConstance.baseUrl))
    }

}



