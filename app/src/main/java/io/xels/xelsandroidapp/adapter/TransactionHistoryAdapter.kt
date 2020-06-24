package io.xels.xelsandroidapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.interfaces.BaseViewHolder
import io.xels.xelsandroidapp.interfaces.OnHistoryClickListener
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.Utils
import java.math.BigDecimal

class TransactionHistoryAdapter(context: Context) :
    BaseRecyclerAdapter<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory, OnHistoryClickListener
    <HistoryApiResponseModel.InnerMsg.History.TransactionsHistory>, TransactionHistoryAdapter.HistoryViewHolder>(
        context
    ) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): HistoryViewHolder {
        return HistoryViewHolder(inflate(R.layout.history_items, viewGroup))
    }

    class HistoryViewHolder(itemView: View) :
        BaseViewHolder<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory,
                OnHistoryClickListener<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory>>(
            itemView
        ) {
        var dateTxtView: TextView
        var amountTxtView: TextView
        var fromAddressTxtView: TextView
        var statusImage: ImageView
        var status: TextView
        var layoutContent: LinearLayout

        init {
            dateTxtView = itemView.findViewById(R.id.dateTxtView)
            amountTxtView = itemView.findViewById(R.id.amountTxtView)
            fromAddressTxtView = itemView.findViewById(R.id.fromAddressTxtView)
            statusImage = itemView.findViewById(R.id.statusImg)
            status = itemView.findViewById(R.id.status)
            layoutContent = itemView.findViewById(R.id.layout_content)
        }

        override fun onBind(
            item: HistoryApiResponseModel.InnerMsg.History.TransactionsHistory,
            listener: OnHistoryClickListener<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory>?
        ) {
            val amount = BigDecimal.valueOf(item.amount.div(AppConstance.shatoshi))
            amountTxtView.text = amount.toString() + " XELS"
            dateTxtView.text = Utils.convertTimeToDate(item.timestamp)

            when (item.type) {
                "staked" -> {
                    fromAddressTxtView.text = item.toAddress
                    if (item.confirmedInBlock > 0) {
                        statusImage.setBackgroundResource(R.drawable.ic_stake_icon)
                    } else {
                        statusImage.setBackgroundResource(R.drawable.ic_stake_icon_not_confirmed)
                    }

                    status.text = "Hybrid Reward"
                }
                "mined" -> {
                    fromAddressTxtView.text = item.toAddress
                    if (item.confirmedInBlock > 0) {
                        statusImage.setBackgroundResource(R.drawable.ic_stake_icon)
                    } else {
                        statusImage.setBackgroundResource(R.drawable.ic_stake_icon_not_confirmed)
                    }

                    status.text = "Pow Reward"
                }
                "send" -> {
                    fromAddressTxtView.text = item.payments.get(0).destinationAddress
                    if (item.confirmedInBlock > 0) {
                        statusImage.setBackgroundResource(R.drawable.ic_send_icon)
                    } else {
                        statusImage.setBackgroundResource(R.drawable.ic_send_icon_not_confirmed)
                    }
                    status.text = "To"

                }
                "received" -> {
                    fromAddressTxtView.text = item.toAddress
                    if (item.confirmedInBlock > 0) {
                        statusImage.setBackgroundResource(R.drawable.ic_receive_icon)
                    } else {
                        statusImage.setBackgroundResource(R.drawable.ic_receive_icon_not_confirmed)
                    }
                    status.text = "From"
                }
            }

            layoutContent.setOnClickListener({
                if (listener != null) {
                    listener.onItemClicked(item)
                }
            })


        }

    }
}