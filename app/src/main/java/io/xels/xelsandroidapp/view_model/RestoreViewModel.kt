package io.xels.xelsandroidapp.view_model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import io.xels.xelsandroidapp.response_model.LoadApiResponseModel
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RestoreViewModel(application: Application) : AndroidViewModel(application) {

    var restoreMutableLiveData: MutableLiveData<LoadApiResponseModel>? = null

    fun getRestoreWallet(
        date: String,
        word: String,
        name: String,
        passPhrase: String,
        password: String
    ): LiveData<LoadApiResponseModel> {
        restoreMutableLiveData = MutableLiveData();
        restoreWallet(date, word, name, passPhrase, password)
        return restoreMutableLiveData as MutableLiveData<LoadApiResponseModel>;
    }

    private fun restoreWallet(date: String, word: String, name: String, passPhrase: String, password: String) {
        val apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        apiInterface?.restoreWalletApi(
            AppConstance.recoverWallet,
            date,
            word,
            name,
            passPhrase,
            password
        )?.enqueue(object : retrofit2.Callback<LoadApiResponseModel?> {
            override fun onFailure(call: Call<LoadApiResponseModel?>, t: Throwable) {
                println(t.printStackTrace())
                restoreMutableLiveData?.value = null
                Toast.makeText(getApplication(), "Something went wrong. please try later", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<LoadApiResponseModel?>,
                response: Response<LoadApiResponseModel?>
            ) {
                if (response.isSuccessful) {
                    restoreMutableLiveData?.value = response.body()
                } else {
                    restoreMutableLiveData?.value = null
                    val jObjError = JSONObject(response.errorBody()?.string())
                    val jArray: JSONArray = jObjError.getJSONArray("InnerMsg")
                    val jObj: JSONObject = jArray.getJSONObject(0)
                    val msg: String = jObj.getString("message")
                    Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show()
                    //Utils.handleErrorResponse(response, this@RestoreWalletActivity, response.code())
                }
            }
        })

    }
}