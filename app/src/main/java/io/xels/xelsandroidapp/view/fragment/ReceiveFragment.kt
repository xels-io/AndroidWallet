package io.xels.xelsandroidapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.response_model.GetUnUsedAddressResponseModel
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import kotlinx.android.synthetic.main.fragment_recieve.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.content.Context.WINDOW_SERVICE
import android.graphics.Bitmap
import android.graphics.Point
import android.util.Log
import android.view.WindowManager
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.model.AddressGetModel
import io.xels.xelsandroidapp.model.CallAddressModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ReceiveFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.copyToClipBoardBtn -> Utils.copyToClipBoard(activity, addressTxtView.text.toString(), "Address")


            R.id.showAllAddressTxtView -> {
                fragment = ShowAllAddressFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(
                    R.id.frameLayout, fragment as ShowAllAddressFragment,
                    "ShowAllAddressFragment"
                )?.addToBackStack("ShowAllAddressFragment")?.commit()
            }

        }


    }


    var apiInterface: ApiInterface? = null
    var qrEcode: QRGEncoder? = null
    var bitmap: Bitmap? = null
    var fragment: Fragment? = null


    var toolBarControll: ToolBarControll? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recieve, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarControll?.setTitle("Receive")
        toolBarControll?.showShareBtn(true)
        toolBarControll?.showDialog(true)
        copyToClipBoardBtn.setOnClickListener(this)
        showAllAddressTxtView.setOnClickListener(this)
        if (Utils.isNetworkAvailable(activity,AppConstance.typeNetwork)){
            getAddress()

        }else{
            Utils.showAlertDialg(activity)
        }


    }

    private fun getAddress() {
        apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)

        apiInterface?.getUnUsedAddress(
            AppConstance.unAddress,
            PreferenceManager.getString(AppConstance.walletName),
            AppConstance.ACCOUNT_NAME
        )
            ?.enqueue(
                object : Callback<GetUnUsedAddressResponseModel?> {
                    override fun onFailure(call: Call<GetUnUsedAddressResponseModel?>, t: Throwable) {

                        Utils.showError(activity)

                        println(t.printStackTrace())
                        toolBarControll?.showDialog(false)


                    }

                    override fun onResponse(
                        call: Call<GetUnUsedAddressResponseModel?>,
                        response: Response<GetUnUsedAddressResponseModel?>
                    ) {

                        if (response.isSuccessful) {
                            addressLayout.visibility = View.VISIBLE
                            addressTxtView.text = response.body()?.innerMsg

                            showQrCode(response)
                            toolBarControll?.showDialog(false)


                        } else {
                            println("dadasdasd")
                            toolBarControll?.showDialog(false)
                            Utils.handleErrorResponse(response, activity, response.code())

                        }

                    }

                })
    }

    private fun showQrCode(response: Response<GetUnUsedAddressResponseModel?>) {
        val manager = activity?.getSystemService(WINDOW_SERVICE) as WindowManager?
        val display = manager!!.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        qrEcode =
            QRGEncoder(response.body()?.innerMsg, null, QRGContents.Type.TEXT, smallerDimension)

        bitmap = qrEcode?.encodeAsBitmap()

        qrCode.setImageBitmap(bitmap)
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
    fun onMessageEvent(event: CallAddressModel) {
        Utils.shareInformation(activity, addressTxtView.text.toString().trim())

    }


}