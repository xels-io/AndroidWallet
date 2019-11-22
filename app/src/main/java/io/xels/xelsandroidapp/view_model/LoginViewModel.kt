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
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var loginMutableLiveData: MutableLiveData<LoadApiResponseModel>? = null


    fun getLoginResponse(wallet: String, password: String): LiveData<LoadApiResponseModel> {
        loginMutableLiveData = MutableLiveData()
        login(wallet, password)
        return loginMutableLiveData as MutableLiveData<LoadApiResponseModel>
    }

    private fun login(wallet: String, password: String){
        val apiInterface: ApiInterface? = ApiClient.getClient()?.create(ApiInterface::class.java)
        apiInterface?.loadApiCall(AppConstance.load,wallet, password)?.enqueue(object : Callback<LoadApiResponseModel> {
            override fun onFailure(call: Call<LoadApiResponseModel>, t: Throwable) {
                loginMutableLiveData?.value = null
            }

            override fun onResponse(call: Call<LoadApiResponseModel>, response: Response<LoadApiResponseModel>) {
                if(response.isSuccessful){
                    loginMutableLiveData!!.value = response.body()
                }else{
                    loginMutableLiveData?.value = null
                    val jObjError = JSONObject(response.errorBody()?.string())
                    val jArray: JSONArray = jObjError.getJSONArray("InnerMsg")
                    val jObj: JSONObject = jArray.getJSONObject(0)
                    val msg: String = jObj.getString("message")
                    Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show()
                }
            }

        })

    }


}