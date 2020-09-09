package io.xels.xelsandroidapp.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.request_model.RecipientModel
import io.xels.xelsandroidapp.response_model.FeeResponseApi
import io.xels.xelsandroidapp.response_model.TransectionApiResponse
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import kotlinx.android.synthetic.main.fragment_send.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.zxing.integration.android.IntentIntegrator
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.view.activity.ScannerAcitivity
import io.xels.xelsandroidapp.request_model.ScannerResultModel
import io.xels.xelsandroidapp.response_model.SendTrasectionApiResponse
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal


class SendFragment : androidx.fragment.app.Fragment(), View.OnClickListener {


    var TAG: String = "SendFragment"
    var apiInterface: ApiInterface? = null
    var recipients: RecipientModel? = null
    var amount: BigDecimal? = null
    var dividedValue: Double? = 0.0
    var toolBarControll: ToolBarControll? = null
    var totalAmount: BigDecimal? = null
    var fragment: androidx.fragment.app.Fragment? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sendBtn -> {
                if (amountEditTxt.text.toString().isEmpty()) {
                    Toast.makeText(activity, resources.getString(R.string.text_enter_amount), Toast.LENGTH_SHORT).show()

                } else if (addressTxtView.text.toString().isEmpty()) {
                    Toast.makeText(activity, resources.getString(R.string.text_enter_address), Toast.LENGTH_SHORT)
                        .show()

                } else if (passwordEditTxtView.text.toString().isEmpty()) {
                    Toast.makeText(activity, resources.getString(R.string.text_enter_password), Toast.LENGTH_SHORT)
                        .show()

                } else {
                    if (Utils.isNetworkAvailable(activity, AppConstance.typeNetwork)) {
                        makeTransection()
                    } else {
                        Utils.showAlertDialg(activity)
                    }
                }
            }
            R.id.scanBarCodeBtn -> {
                initScanner()
            }
            R.id.layout -> {
                Utils.hideKeyBoard(activity)
            }

            R.id.cancelBtn ->{
                activity!!.supportFragmentManager.popBackStack()
                toolBarControll?.enableSelectedDrawer()
            }
        }
    }

    fun initScanner(){
        val scanner = IntentIntegrator.forSupportFragment(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        scanner.setPrompt("Scan a QR code");
        scanner.setBeepEnabled(false)
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
            if(result!=null){
                //Toast.makeText(activity,result.contents,Toast.LENGTH_SHORT).show()
                addressTxtView.setText(result.contents)
            }else{
                //super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    /*private fun makeTransection(){
        toolBarControll?.showDialog(true)
        val jArray: JsonArray = JsonArray()
        val jObject= JSONObject()
        jObject.put("destinationAddress",addressTxtView.text.toString())
        jObject.put("amount",amountEditTxt.text.toString())

        val stringJson = jObject.toString()
        jArray.add(stringJson)

        val stringArray = jArray.toString()

        //Toast.makeText(activity?.applicationContext, stringArray, Toast.LENGTH_SHORT).show()
        apiInterface?.makeNewTransaction(AppConstance.transection, PreferenceManager.getString(AppConstance.walletName),AppConstance.ACCOUNT_NAME,stringArray, feeEditText.text.toString(),
            true,
            passwordEditTxtView.text.toString()
            ,
            false)?.enqueue(object : Callback<TransectionApiResponse> {
            override fun onFailure(call: Call<TransectionApiResponse>, t: Throwable) {
                println(t.printStackTrace())
                println("try later")
                Utils.showError(activity)
                toolBarControll?.showDialog(false)

            }

            override fun onResponse(
                call: Call<TransectionApiResponse>,
                response: Response<TransectionApiResponse>
            ) {

                if (response.isSuccessful) {

                    apiInterface!!.sendTrasactionApi(AppConstance.sendTransection, response.body()?.innerMsg!!.hex)
                        .enqueue(
                            object : Callback<SendTrasectionApiResponse> {

                                override fun onFailure(call: Call<SendTrasectionApiResponse>, t: Throwable) {
                                    toolBarControll?.showDialog(false)
                                }

                                override fun onResponse(
                                    call: Call<SendTrasectionApiResponse>,
                                    response: Response<SendTrasectionApiResponse>
                                ) {

                                    if (response.isSuccessful) {
                                        //Toast.makeText(activity, "Transaction complete", Toast.LENGTH_SHORT).show()
                                        Utils.showTransactionSuccess(activity)
                                        amountEditTxt.setText("")
                                        addressTxtView.setText("")
                                        passwordEditTxtView.setText("")
                                        feeEditText.setText(R.string.please_enter_a_valid_fee)
                                    } else {
                                        Utils.handleErrorResponse(response, activity, response.code())

                                    }
                                    toolBarControll?.showDialog(false)
                                }

                            })


                } else {
                    Toast.makeText(activity, "Something went wrong, please try later", Toast.LENGTH_SHORT)
                        .show()
                    toolBarControll?.showDialog(false)

                }
            }

        })
    }*/

    private fun makeTransection() {
        toolBarControll?.showDialog(true)
        apiInterface?.makeTransaction(
            AppConstance.transection,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME,
            addressTxtView.text.toString(),
            amountEditTxt.text.toString(),
            feeEditText.text.toString(),
            true,
            passwordEditTxtView.text.toString()
            ,
            false
        )?.enqueue(object : Callback<TransectionApiResponse> {
            override fun onFailure(call: Call<TransectionApiResponse>, t: Throwable) {
                println(t.printStackTrace())
                println("try later")
                Utils.showError(activity)
                toolBarControll?.showDialog(false)

            }

            override fun onResponse(
                call: Call<TransectionApiResponse>,
                response: Response<TransectionApiResponse>
            ) {

                if (response.isSuccessful) {

                    apiInterface!!.sendTrasactionApi(AppConstance.sendTransection, response.body()?.innerMsg!!.hex)
                        .enqueue(
                            object : Callback<SendTrasectionApiResponse> {

                                override fun onFailure(call: Call<SendTrasectionApiResponse>, t: Throwable) {
                                    toolBarControll?.showDialog(false)
                                }

                                override fun onResponse(
                                    call: Call<SendTrasectionApiResponse>,
                                    response: Response<SendTrasectionApiResponse>
                                ) {

                                    if (response.isSuccessful) {
                                        //Toast.makeText(activity, "Transaction complete", Toast.LENGTH_SHORT).show()
                                        Utils.showTransactionSuccess(activity)
                                        amountEditTxt.setText("")
                                        addressTxtView.setText("")
                                        passwordEditTxtView.setText("")
                                        feeEditText.setText(R.string.please_enter_a_valid_fee)
                                    } else {
                                        Utils.handleErrorResponse(response, activity, response.code())

                                    }
                                    toolBarControll?.showDialog(false)
                                }

                            })


                } else {
                    Toast.makeText(activity, "Something went wrong, please try later", Toast.LENGTH_SHORT)
                        .show()
                    toolBarControll?.showDialog(false)

                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarControll?.setTitle("Send")
        toolBarControll?.showShareBtn(false)
        sendBtn.setOnClickListener(this)
        layout.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
        scanBarCodeBtn.setOnClickListener(this)
        totalAmount = BigDecimal.valueOf(PreferenceManager.getLong(AppConstance.balance).div(AppConstance.shatoshi))
        amountAvailableTxtView.text = totalAmount.toString()
        apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)

        amountEditTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.e(TAG, "afterTextChanged")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e(TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e(TAG, "onTextChanged")
                if (!addressTxtView.text.isEmpty() && addressTxtView.text.length == 34) {

                    if (Utils.isNetworkAvailable(activity, AppConstance.typeNetwork)) {
                        getFee(apiInterface)

                    } else {
                        Utils.showAlertDialg(activity)
                    }

                } else if (addressTxtView.text.length < 34) {
                    feeEditText.setText(R.string.please_enter_a_valid_fee)
                } else if (addressTxtView.text.isEmpty()) {
                    feeEditText.setText(R.string.please_enter_a_valid_fee)

                }
            }

        })


        addressTxtView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.e(TAG, "afterTextChanged")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e(TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e(TAG, "onTextChanged")
                if (!addressTxtView.text.isEmpty() && addressTxtView.text.length == 34) {

                    if (Utils.isNetworkAvailable(activity, AppConstance.typeNetwork)) {
                        getFee(apiInterface)

                    } else {
                        Utils.showAlertDialg(activity)
                    }

                } else if (addressTxtView.text.length < 34) {
                    feeEditText.setText(R.string.please_enter_a_valid_fee)
                } else if (addressTxtView.text.isEmpty()) {
                    feeEditText.setText(R.string.please_enter_a_valid_fee)

                }
            }

        })

    }

    private fun getFee(apiInterface: ApiInterface?) {
        apiInterface?.getEstimateTax(
            AppConstance.estimateTax,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME, addressTxtView.text.toString(),
            amountEditTxt.text.toString(), "medium", true
        )?.enqueue(object : Callback<FeeResponseApi> {
            override fun onResponse(call: Call<FeeResponseApi>, response: Response<FeeResponseApi>) {
                if (response.isSuccessful) {

                    amount = BigDecimal.valueOf(response.body()?.innerMsg!!.div(AppConstance.shatoshi))
                    feeEditText.text = amount.toString()
                } else {
                    feeEditText.setText(R.string.please_enter_a_valid_fee)
                    //Utils.handleErrorResponse(response, activity, response.code())
                }
            }

            override fun onFailure(call: Call<FeeResponseApi>, t: Throwable) {
                Utils.showError(activity)
                println(t.printStackTrace())
                feeEditText.setText(R.string.please_enter_a_valid_fee)
            }
        })
    }

    /*private fun getFee(apiInterface: ApiInterface?){
        val jArray: JSONArray = JSONArray()
        val jObject= JSONObject()
        jObject.put("destinationAddress",addressTxtView.text.toString())
        jObject.put("amount",amountEditTxt.text.toString().toIntOrNull())

        val stringJson = jObject.toString()
        jArray.put(jObject)

        val stringArray = jArray.toString()
        apiInterface?.getNewEstimateTax(AppConstance.estimateTax,
            PreferenceManager.getString(AppConstance.walletName),AppConstance.ACCOUNT_NAME, jArray.toString(), "medium", true)?.enqueue(object : Callback<FeeResponseApi> {
            override fun onResponse(call: Call<FeeResponseApi>, response: Response<FeeResponseApi>) {
                if (response.isSuccessful) {

                    amount = BigDecimal.valueOf(response.body()?.innerMsg!!.div(AppConstance.shatoshi))
                    feeEditText.text = amount.toString()
                } else {
                    feeEditText.setText(R.string.please_enter_a_valid_fee)
                    //Utils.handleErrorResponse(response, activity, response.code())
                }
            }

            override fun onFailure(call: Call<FeeResponseApi>, t: Throwable) {
                Utils.showError(activity)
                println(t.printStackTrace())
                feeEditText.setText(R.string.please_enter_a_valid_fee)
            }
        })
    }*/

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ToolBarControll) {
            toolBarControll = context as ToolBarControll?
        } else {
            throw IllegalArgumentException("Containing activity must implement OnSearchListener interface")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ScannerResultModel) {
        addressTxtView.setText(event.address)

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}