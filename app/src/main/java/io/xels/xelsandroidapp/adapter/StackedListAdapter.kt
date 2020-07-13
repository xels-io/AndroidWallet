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

class StackedListAdapter(
    var body: HistoryApiResponseModel?/*,var context:FragmentActivity*/,
    var type: Int
) :
    RecyclerView.Adapter<StackedListAdapter.ViewHolder>() {


    var amount: BigDecimal? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {


        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.history_items, p0, false)
        return ViewHolder(view)

    }


    override fun getItemCount(): Int {
        return body!!.innerMsg.history[0].transactionsHistory.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        if (type == 0) {
            when (body!!.innerMsg.history[0].transactionsHistory.get(p1).type) {
                "staked" -> {
                    p0.fromAddressTxtView.text =
                        body!!.innerMsg.history[0].transactionsHistory.get(p1).toAddress
                    p0.statusImage.setBackgroundResource(R.drawable.stake)
                    p0.status.text = "Hybrid Reward"


                    amount = BigDecimal.valueOf(
                        body!!.innerMsg.history[0].transactionsHistory.get(p1).amount.div(
                            AppConstance.shatoshi
                        )
                    )

                    p0.amountTxtView.text = amount.toString() + " XELS"
                    p0.dateTxtView.text =
                        Utils.convertTimeToDate(
                            body!!.innerMsg.history[0].transactionsHistory.get(
                                p1
                            ).timestamp
                        )


                }
            }
        } else if (type == 1) {
            when (body!!.innerMsg.history[0].transactionsHistory.get(p1).type) {
                "Mined" -> {
                    p0.fromAddressTxtView.text =
                        body!!.innerMsg.history[0].transactionsHistory.get(p1).toAddress
                    p0.statusImage.setBackgroundResource(R.drawable.stake)
                    p0.status.text = "Pow Reward"


                    amount = BigDecimal.valueOf(
                        body!!.innerMsg.history[0].transactionsHistory.get(p1).amount.div(
                            AppConstance.shatoshi
                        )
                    )

                    p0.amountTxtView.text = amount.toString() + " XELS"
                    p0.dateTxtView.text =
                        Utils.convertTimeToDate(body!!.innerMsg.history[0].transactionsHistory.get(p1).timestamp)


                }
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateTxtView: TextView = itemView.findViewById(R.id.dateTxtView)
        val amountTxtView: TextView = itemView.findViewById(R.id.amountTxtView)
        val fromAddressTxtView: TextView = itemView.findViewById(R.id.fromAddressTxtView)
        val statusImage: ImageView = itemView.findViewById(R.id.statusImg)
        val status: TextView = itemView.findViewById(R.id.status)

    }
}