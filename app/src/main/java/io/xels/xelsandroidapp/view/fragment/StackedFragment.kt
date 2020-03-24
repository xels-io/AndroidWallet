package io.xels.xelsandroidapp.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.adapter.HistoryRvAdapter
import io.xels.xelsandroidapp.adapter.StackedListAdapter
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.retrofit.ApiClient
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import io.xels.xelsandroidapp.ulits.Utils
import io.xels.xelsandroidapp.view_model.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_stacked.*
import kotlinx.android.synthetic.main.fragment_transaction_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackedFragment : Fragment() {


    private var toolBarControll: ToolBarControll? = null
    var apiInterface: ApiInterface? = null
    lateinit var noData: TextView
    lateinit var stackedAdapet: StackedListAdapter
    private var historyViewModel: HistoryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolBarControll?.setTitle(getString(R.string.stacked))
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stacked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        toolBarControll?.showDialog(true)

        historyViewModel!!.getTranscationHistory().observe(this, Observer { historyResponse ->
            if (historyResponse != null) {
                if (historyResponse.innerMsg.history[0].transactionsHistory.isNotEmpty()) {
                    stacked_rv.visibility = View.VISIBLE
                    noData.visibility = View.GONE

                    stackedAdapet = StackedListAdapter(historyResponse)

                    stacked_rv.setAdapter(stackedAdapet)
                } else {
                    noData.visibility = View.GONE
                }

            } else {
                noData.visibility = View.GONE
            }

            toolBarControll?.showDialog(false)

        })


    }

    private fun init(view: View) {
        noData = view.findViewById(R.id.noData)
        val mLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        stacked_rv.setLayoutManager(mLayoutManager)
        stacked_rv.setItemAnimator(DefaultItemAnimator())
        stacked_rv.setHasFixedSize(true)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ToolBarControll) toolBarControll = context else {
            throw IllegalArgumentException("Containing activity must implement OnSearchListener interface")
        }
    }

}