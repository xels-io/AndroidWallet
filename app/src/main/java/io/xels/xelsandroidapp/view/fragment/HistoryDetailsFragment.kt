package io.xels.xelsandroidapp.view.fragment


import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.Utils
import io.xels.xelsandroidapp.view_model.HistoryDetailsViewModel
import kotlinx.android.synthetic.main.fragment_history_details.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*


class HistoryDetailsFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_ok -> {
                activity?.supportFragmentManager?.popBackStack()
            }
            R.id.btn_copy -> {
                Utils.copyToClipBoard(activity, tv_tran_id.text.toString(), "Address")

            }
        }
    }

    private var data: HistoryApiResponseModel.InnerMsg.History.TransactionsHistory? = null
    private var historyDetailsViewModel: HistoryDetailsViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            this.data = arguments!!.getParcelable("item");
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_details, container, false)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyDetailsViewModel = ViewModelProviders.of(this).get(HistoryDetailsViewModel::class.java)
        val amount = BigDecimal.valueOf(data?.amount?.div(AppConstance.shatoshi)!!)
        tv_total_amount.text = amount.toString() + " " + resources.getString(R.string.text_xels)
        tv_block.text = "#" + data?.confirmedInBlock.toString()
        tv_date.text = convertedDate(data!!.timestamp.toLong())
        tv_tran_id.text = data!!.id

        if (data?.type.equals("received")) {
            tv_type.text = data?.type.toString().toUpperCase()
            layout_amount_sent.visibility = View.GONE
            layout_fee.visibility = View.GONE
        } else if (data?.type.equals("staked")) {
            tv_type.text = resources.getString(R.string.text_capital_reward)
            layout_amount_sent.visibility = View.GONE
            layout_fee.visibility = View.GONE


        } else {
            tv_total_amount.setTextColor(resources.getColor(R.color.red))
            tv_type.text = data?.type.toString().toUpperCase()
            val amountSent = BigDecimal.valueOf(data?.payments?.get(0)?.amount?.div(AppConstance.shatoshi)!!)
            tv_amount_sent.text = amountSent.toString() + " " + resources.getString(R.string.text_xels)
            tv_fee.text = data!!.fee.toString()
            if (data?.confirmedInBlock == 0) {
                layout_block.visibility = View.GONE
                tv_confirmation.text = resources.getString(R.string.text_unconfirmed)
            }
        }

        btn_ok.setOnClickListener(this)
        btn_copy.setOnClickListener(this)
        getConfirmation()

    }

    private fun getConfirmation() {
        historyDetailsViewModel?.getConfirmedResponse()
            ?.observe(this, android.arch.lifecycle.Observer { transactionConfirm ->
                if (transactionConfirm != null) {
                    if (data?.confirmedInBlock == 0) {
                        tv_confirmation.background = resources.getDrawable(R.drawable.bg_unconfirmed_tv, null)
                        layout_block.visibility = View.GONE
                        tv_confirmation.text = resources.getString(R.string.text_unconfirmed)

                    } else {
                        tv_confirmation.background = resources.getDrawable(R.drawable.bg_history_details_copy_btn, null)
                        val confirmation =
                            transactionConfirm.innerMsg.lastBlockSyncedHeight.minus(data?.confirmedInBlock!!)
                        tv_confirmation.text = (confirmation + 1).toString()

                    }
                }
            })

    }

    @SuppressLint("SimpleDateFormat")
    private fun convertedDate(time: Long): String? {
        try {
            val sdf = SimpleDateFormat(resources.getString(R.string.text_date_format))
            val netDate = Date(time.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Throwable) {
            return e.toString()
        }
    }


}
