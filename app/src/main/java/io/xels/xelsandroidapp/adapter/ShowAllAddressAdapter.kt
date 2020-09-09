package io.xels.xelsandroidapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.model.AddressGetModel
import io.xels.xelsandroidapp.response_model.AllAddressApiResponse
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.Utils
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal

class ShowAllAddressAdapter (var body: ArrayList<String>?) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ShowAllAddressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {


        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.show_all_address_item, p0, false)
        return ViewHolder(view)

    }


    override fun getItemCount(): Int {
        return body!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.mAddressTxtView.setText(body?.get(p1))

        p0.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                EventBus.getDefault().post(AddressGetModel(body?.get(p1)))
            }
        })

    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val mAddressTxtView: TextView = itemView.findViewById(R.id.addressTxtView)

    }





}