package io.xels.xelsandroidapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.Utils
import java.math.BigDecimal

class HistoryAdapter(var itemList: ArrayList<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var amount: BigDecimal? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HistoryViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.history_items, p0, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun clearList() {
        itemList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: HistoryViewHolder, p1: Int) {
        amount = BigDecimal.valueOf(itemList.get(p1).amount.div(AppConstance.shatoshi))
        p0.amountTxtView.text = amount.toString() + " XELS"
        p0.dateTxtView.text = Utils.convertTimeToDate(itemList.get(p1).timestamp)

        when (itemList.get(p1).type) {
            "staked" -> {
                p0.fromAddressTxtView.text = itemList.get(p1).toAddress
                if (itemList.get(p1).confirmedInBlock > 0) {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_stake_icon)
                } else {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_stake_icon_not_confirmed)
                }

                p0.status.text = "Hybrid Reward"
            }
            "mined" -> {
                p0.fromAddressTxtView.text = itemList.get(p1).toAddress
                if (itemList.get(p1).confirmedInBlock > 0) {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_stake_icon)
                } else {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_stake_icon_not_confirmed)
                }

                p0.status.text = "Pow Reward"
            }
            "send" -> {
                p0.fromAddressTxtView.text = itemList.get(p1).payments.get(0).destinationAddress
                if (itemList.get(p1).confirmedInBlock > 0) {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_send_icon)
                } else {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_send_icon_not_confirmed)
                }
                p0.status.text = "To"

            }
            "received" -> {
                p0.fromAddressTxtView.text = itemList.get(p1).toAddress
                if (itemList.get(p1).confirmedInBlock > 0) {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_receive_icon)
                } else {
                    p0.statusImage.setBackgroundResource(R.drawable.ic_receive_icon_not_confirmed)
                }
                p0.status.text = "From"
            }
        }
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTxtView: TextView = itemView.findViewById(R.id.dateTxtView)
        val amountTxtView: TextView = itemView.findViewById(R.id.amountTxtView)
        val fromAddressTxtView: TextView = itemView.findViewById(R.id.fromAddressTxtView)
        val statusImage: ImageView = itemView.findViewById(R.id.statusImg)
        val status: TextView = itemView.findViewById(R.id.status)

    }
}