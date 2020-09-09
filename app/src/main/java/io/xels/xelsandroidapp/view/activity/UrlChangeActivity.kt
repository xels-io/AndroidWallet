package io.xels.xelsandroidapp.view.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils

class UrlChangeActivity : androidx.fragment.app.FragmentActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.okBTn -> {
                PreferenceManager.updateValue(AppConstance.baseUrl, url?.text.toString())
                ApiClient.destroyInstance()
                finish()

            }
            R.id.container -> {
                Utils.hideKeyBoard(this@UrlChangeActivity)
            }

        }
    }

    var url: EditText? = null
    var okBTn: Button? = null
    var container: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_url)
        url = findViewById(R.id.tv_url) as EditText
        okBTn = findViewById(R.id.okBTn) as Button
        container = findViewById(R.id.container) as RelativeLayout
        url?.setText(PreferenceManager.getString(AppConstance.baseUrl))
        container?.setOnClickListener(this)
        okBTn?.setOnClickListener(this)

        //Toast.makeText(this, PreferenceManager.getString(AppConstance.baseUrl), Toast.LENGTH_SHORT).show()

    }
}