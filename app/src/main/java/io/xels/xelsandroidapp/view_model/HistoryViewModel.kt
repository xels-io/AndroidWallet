package io.xels.xelsandroidapp.view_model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import io.xels.xelsandroidapp.adapter.HistoryAdapter
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import kotlinx.android.synthetic.main.fragment_history.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    var historyApiResponseModel: MutableLiveData<HistoryApiResponseModel>? = null

    fun getTranscationHistory(): LiveData<HistoryApiResponseModel> {
        historyApiResponseModel = MutableLiveData()
        //login(wallet, password)
        getHistory()
        return historyApiResponseModel as MutableLiveData<HistoryApiResponseModel>
    }

    private fun getHistory() {
        val apiInterface: ApiInterface? = ApiClient.getClient()?.create(ApiInterface::class.java)
        apiInterface?.getHistory(
            AppConstance.getHistory,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME
        )?.enqueue(object :
            Callback<HistoryApiResponseModel?> {
            override fun onFailure(call: Call<HistoryApiResponseModel?>, t: Throwable) {
                println(t.printStackTrace())
                historyApiResponseModel!!.value = null
                Toast.makeText(getApplication(), "Something went wrong, Please try later", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<HistoryApiResponseModel?>,
                response: Response<HistoryApiResponseModel?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.innerMsg?.history?.get(0)?.transactionsHistory?.size != 0) {
                        historyApiResponseModel!!.value = response.body()
                    } else {
                        historyApiResponseModel!!.value = response.body()
                    }

                } else {
                    historyApiResponseModel!!.value = null
                    handleErrorResponse(response)
                }
            }
        })

    }

    fun handleErrorResponse(
        response: Response<*>
    ) {
        val jObjError = JSONObject(response.errorBody()?.string())
        var jArray: JSONArray = jObjError.getJSONArray("InnerMsg")
        var jObj: JSONObject = jArray.getJSONObject(0)
        var msg: String = jObj.getString("message")
        Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show()
    }

}