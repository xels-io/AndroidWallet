package io.xels.xelsandroidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.model.ListAdapterData
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.Utils
import java.math.BigDecimal

class StackedListAdapter(
    var body: ArrayList<ListAdapterData>/*,var context:FragmentActivity*/,
    var type: Int
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<StackedListAdapter.ViewHolder>() {


    var amount: BigDecimal? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {


        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.history_items, p0, false)
        return ViewHolder(view)

    }


    override fun getItemCount(): Int {
        return body.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        if (type == 0) {
            when (body[p1].type) {
                "staked" -> {
                    p0.fromAddressTxtView.text =
                        body!!.get(p1).toAddress
                    p0.statusImage.setBackgroundResource(R.drawable.stake)
                    p0.status.text = "Hybrid Reward"


                    amount = BigDecimal.valueOf(
                        body!!.get(p1).amount.div(
                            AppConstance.shatoshi
                        )
                    )

                    p0.amountTxtView.text = amount.toString() + " XELS"
                    p0.dateTxtView.text =
                        Utils.convertTimeToDate(
                            body!!.get(
                                p1
                            ).timeStamp
                        )


                }
            }
        } else if (type == 1) {
            when (body!!.get(p1).type.toLowerCase()) {
                "mined" -> {
                    p0.fromAddressTxtView.text =
                        body!!.get(p1).toAddress
                    p0.statusImage.setBackgroundResource(R.drawable.stake)
                    p0.status.text = "Pow Reward"


                    amount = BigDecimal.valueOf(
                        body!!.get(p1).amount.div(
                            AppConstance.shatoshi
                        )
                    )

                    p0.amountTxtView.text = amount.toString() + " XELS"
                    p0.dateTxtView.text =
                        Utils.convertTimeToDate(body!!.get(p1).timeStamp)


                }
            }
        }


    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val dateTxtView: TextView = itemView.findViewById(R.id.dateTxtView)
        val amountTxtView: TextView = itemView.findViewById(R.id.amountTxtView)
        val fromAddressTxtView: TextView = itemView.findViewById(R.id.fromAddressTxtView)
        val statusImage: ImageView = itemView.findViewById(R.id.statusImg)
        val status: TextView = itemView.findViewById(R.id.status)

    }
}