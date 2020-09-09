package io.xels.xelsandroidapp.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.widget.Toast
import io.xels.xelsandroidapp.model.TransactionConfirmedResponse
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    var transactionConfirmedResponse: MutableLiveData<TransactionConfirmedResponse>? = null


    fun getConfirmedResponse() : LiveData<TransactionConfirmedResponse> {
        transactionConfirmedResponse = MutableLiveData()
        getTransactionconfirmed()
        return transactionConfirmedResponse as MutableLiveData<TransactionConfirmedResponse>
    }

    private fun getTransactionconfirmed(){
        val apiInterface: ApiInterface? = ApiClient.getClient()?.create(ApiInterface::class.java)
        apiInterface?.getGeneralInfo(AppConstance.confirmation, PreferenceManager.getString(AppConstance.walletName))
            ?.enqueue(object :
                Callback<TransactionConfirmedResponse?>{
                override fun onResponse(
                    call: Call<TransactionConfirmedResponse?>,
                    response: Response<TransactionConfirmedResponse?>
                ) {
                    if(response.isSuccessful){
                        if(response.body()?.innerMsg!=null){
                            transactionConfirmedResponse?.value = response.body()
                        }else{
                            transactionConfirmedResponse!!.value = null
                        }
                    }else{
                        transactionConfirmedResponse!!.value = null
                    }
                }

                override fun onFailure(call: Call<TransactionConfirmedResponse?>, t: Throwable) {
                    transactionConfirmedResponse!!.value = null
                    Toast.makeText(getApplication(), "Something went wrong, Please try later", Toast.LENGTH_LONG).show()

                }
            })
    }
}