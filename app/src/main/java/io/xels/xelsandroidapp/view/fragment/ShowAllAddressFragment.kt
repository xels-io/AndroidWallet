package io.xels.xelsandroidapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.adapter.ShowAllAddressAdapter
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.model.AddressGetModel
import io.xels.xelsandroidapp.response_model.AllAddressApiResponse
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import kotlinx.android.synthetic.main.show_all_address.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowAllAddressFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.usedBtn -> {
                toolBarControll?.setTitle("Used Address")
                showAddress(usedAddress)
            }
            R.id.unusedBtn -> {
                toolBarControll?.setTitle("Unused Address")
                showAddress(unUsedAddress)

            }
            R.id.changedBtn -> {
                toolBarControll?.setTitle("Changed Address")
                showAddress(changed)
            }
        }
    }

    var apiInterface: ApiInterface? = null
    var toolBarControll: ToolBarControll? = null
    var showAllAddressAdapter: ShowAllAddressAdapter? = null
    var usedAddress: ArrayList<String>? = ArrayList()
    var unUsedAddress: ArrayList<String>? = ArrayList()
    var changed: ArrayList<String>? = ArrayList()

    var noData: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolBarControll?.setTitle("Used Address")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.show_all_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noData = view.findViewById(R.id.noData)
        toolBarControll?.showShareBtn(false)
        listener()

        if (Utils.isNetworkAvailable(activity, AppConstance.typeNetwork)) {
            showAllAddress()

        } else {
            Utils.showAlertDialg(activity)
        }
    }

    private fun listener() {
        usedBtn.setOnClickListener(this)
        unusedBtn.setOnClickListener(this)
        changedBtn.setOnClickListener(this)
    }

    private fun showAllAddress() {
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
        //toolBarControll?.showDialog(true)
        apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
        apiInterface?.getAllAddress(
            AppConstance.getAddress,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME
        )?.enqueue(object :
            Callback<AllAddressApiResponse?> {
            override fun onFailure(call: Call<AllAddressApiResponse?>, t: Throwable) {
                println(t.printStackTrace())
                Utils.showError(activity)
                shimmer_view_container.visibility = View.GONE
                shimmer_view_container.stopShimmer()
                //toolBarControll?.showDialog(false)

            }

            override fun onResponse(
                call: Call<AllAddressApiResponse?>,
                response: Response<AllAddressApiResponse?>
            ) {
                if (response.isSuccessful) {

                    if (response.body()?.innerMsg?.addresses?.size != 0) {
                        allAddress.visibility = View.VISIBLE
                        for (item in response.body()?.innerMsg?.addresses!!) {
                            print(item)

                            if (item.isChange) {
                                changed?.add(item.address)
                            }
                            if (item.isUsed) {
                                usedAddress?.add(item.address)

                            } else {
                                unUsedAddress?.add(item.address)
                            }
                        }

                        showAddress(usedAddress)
                    } else {
                        allAddress.visibility = View.GONE

                    }
                    shimmer_view_container.visibility = View.GONE
                    shimmer_view_container.stopShimmer()

                } else {
                    Utils.handleErrorResponse(response, activity, response.code())
                    shimmer_view_container.visibility = View.GONE
                    shimmer_view_container.stopShimmer()
                }
            }

        })
    }

    private fun showAddress(response: ArrayList<String>?) {
        if (response?.size!! > 0) {
            noData?.visibility = View.GONE
            allAddress?.visibility = View.VISIBLE
            showAllAddressAdapter = ShowAllAddressAdapter(response)
            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            allAddress.setLayoutManager(mLayoutManager)
            allAddress.setItemAnimator(DefaultItemAnimator())
            allAddress.setAdapter(showAllAddressAdapter)
            allAddress.setHasFixedSize(true)
        } else {
            noData?.visibility = View.VISIBLE
            allAddress?.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ToolBarControll) {
            toolBarControll = context as ToolBarControll?
        } else {
            throw IllegalArgumentException("Containing activity must implement OnSearchListener interface")
        }
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: AddressGetModel) {
        Log.e("adas", event.address)
        Utils.showOptionDialog(activity, event.address)
    }


}
