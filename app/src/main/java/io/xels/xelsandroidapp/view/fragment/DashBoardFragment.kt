package io.xels.xelsandroidapp.view.fragment

import android.content.Context
import android.icu.util.TimeUnit
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.adapter.HistoryAdapter
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.response_model.GetBalanceApiResponse
import io.xels.xelsandroidapp.response_model.GetStakingInfoResponse
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.xml.datatype.DatatypeConstants.SECONDS


class DashBoardFragment : androidx.fragment.app.Fragment() {

    var amountConfirmed: BigDecimal? = null
    var unAmountConfirmed: BigDecimal? = null
    var totalAmount: Long? = null
    var toolBarControll: ToolBarControll? = null
    var mAdapter: HistoryAdapter? = null
    var transcationList: ArrayList<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory> = ArrayList()
    var historyList: ArrayList<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory> = ArrayList()
    lateinit var df: DecimalFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarControll?.setTitle("DashBoard")
        //toolBarControll?.showDialog(true)
        toolBarControll?.showShareBtn(false)
        val apiInterface: ApiInterface? = ApiClient.getClient()?.create(ApiInterface::class.java)
        if (Utils.isNetworkAvailable(activity, AppConstance.typeNetwork)) {
            callBalance(apiInterface)
            getTransactionHistory(apiInterface)

        } else {
            Utils.showAlertDialg(activity)
        }
        initListener()

    }

    fun initListener() {
        tv_show_full_history.setOnClickListener({
            toolBarControll?.loadFragmentByPosition(1)

        })

        btn_send.setOnClickListener({
            toolBarControll?.loadFragmentByPosition(3)
        })

        btn_receive.setOnClickListener({
            toolBarControll?.loadFragmentByPosition(2)
        })

    }

    fun getTransactionHistory(apiInterface: ApiInterface?) {
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
        apiInterface?.getHistory(
            AppConstance.getHistory,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME
        )?.enqueue(object :
            Callback<HistoryApiResponseModel?> {
            override fun onFailure(call: Call<HistoryApiResponseModel?>, t: Throwable) {
                println(t.printStackTrace())
                Utils.showError(activity)
                shimmer_view_container.visibility = View.GONE
                shimmer_view_container.stopShimmer()
                toolBarControll?.showDialog(false)
                //toolBarControll?.showDialog(false)
            }

            override fun onResponse(
                call: Call<HistoryApiResponseModel?>,
                response: Response<HistoryApiResponseModel?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.innerMsg?.history?.get(0)?.transactionsHistory?.size != 0) {
                        if(response.body()?.innerMsg?.history?.get(0)?.transactionsHistory?.size!! >5){
                            for (i in 0..4) {
                                transcationList.add(response.body()?.innerMsg?.history?.get(0)?.transactionsHistory!![i])
                            }
                        }else{
                            for (i in response.body()?.innerMsg?.history?.get(0)?.transactionsHistory!!.indices) {
                                transcationList.add(response.body()?.innerMsg?.history?.get(0)?.transactionsHistory!![i])
                            }
                        }

                        mAdapter = HistoryAdapter(transcationList)
                        val mLayoutManager =
                            androidx.recyclerview.widget.LinearLayoutManager(
                                activity,
                                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                                false
                            )
                        rv_history.setLayoutManager(mLayoutManager)
                        rv_history.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
                        rv_history.setAdapter(mAdapter)
                        rv_history.setHasFixedSize(true)
                        shimmer_view_container.visibility = View.GONE
                        shimmer_view_container.stopShimmer()
                        toolBarControll?.showDialog(false)
                    } else {
                        shimmer_view_container.visibility = View.GONE
                        shimmer_view_container.stopShimmer()
                        tv_no_history.visibility = View.VISIBLE
                        toolBarControll?.showDialog(false)
                    }

                } else {
                    shimmer_view_container.visibility = View.GONE
                    shimmer_view_container.stopShimmer()
                    toolBarControll?.showDialog(false)
                    Utils.handleErrorResponse(response, activity, response.code())
                }
            }
        })
    }

    private fun callBalance(apiInterface: ApiInterface?) {
        toolBarControll?.showDialog(true)
        apiInterface?.getBalance(
            AppConstance.getBalance,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME
        )?.enqueue(object :
            retrofit2.Callback<GetBalanceApiResponse?> {
            override fun onFailure(call: Call<GetBalanceApiResponse?>, t: Throwable) {
                println(t.printStackTrace())
                Utils.showError(activity)
                //toolBarControll?.showDialog(false)


            }

            override fun onResponse(call: Call<GetBalanceApiResponse?>, response: Response<GetBalanceApiResponse?>) {

                if (response.isSuccessful) {
                    amountConfirmed = BigDecimal.valueOf(
                        response.body()?.innerMsg?.balances?.get(0)!!.amountConfirmed?.div(AppConstance.shatoshi)
                    )
                    unAmountConfirmed = BigDecimal.valueOf(
                        response.body()!!.innerMsg.balances.get(0).amountUnconfirmed.div(AppConstance.shatoshi)
                    )
                    Log.e("amount", "" + "%.8f".format(amountConfirmed))

                    confirmedBalanceTxtView.text = amountConfirmed.toString() + " XELS"
                    unConfirmedBalanceTxtView.text = unAmountConfirmed.toString() + " (unconfirmed)"

                    totalAmount = response.body()?.innerMsg?.balances?.get(0)!!.amountConfirmed?.plus(
                        response.body()!!.innerMsg.balances.get(0).amountUnconfirmed
                    )
                    PreferenceManager.updateValue(AppConstance.balance, totalAmount)
                    //toolBarControll?.showDialog(false)
                } else {
                    //toolBarControll?.showDialog(false)
                    Utils.handleErrorResponse(response, activity, response.code())

                }
            }

        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ToolBarControll) {
            toolBarControll = context as ToolBarControll?
        } else {
            throw IllegalArgumentException("Containing activity must implement OnSearchListener interface")
        }
    }

}
