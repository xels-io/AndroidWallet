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

import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.adapter.TransactionHistoryAdapter
import io.xels.xelsandroidapp.interfaces.OnHistoryClickListener
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.response_model.HistoryApiResponseModel
import io.xels.xelsandroidapp.view_model.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_transaction_history.*


class TransactionHistoryFragment : Fragment(),
    OnHistoryClickListener<HistoryApiResponseModel.InnerMsg.History.TransactionsHistory> {
    override fun onItemClicked(item: HistoryApiResponseModel.InnerMsg.History.TransactionsHistory) {
        val fragment = HistoryDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("item", item)
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(android.R.id.content, fragment, "HistoryFragment")
            ?.addToBackStack("HistoryFragment")
            ?.commit()

    }

    var toolBarControll: ToolBarControll? = null
    private var historyViewModel: HistoryViewModel? = null
    private var mAdapter: TransactionHistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarControll?.showShareBtn(false)
        toolBarControll?.setTitle("History")
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_tran_history.layoutManager = mLayoutManager
        rv_tran_history.setItemAnimator(DefaultItemAnimator())
        loadHistory()

        swipe_layout.setOnRefreshListener({
            if (mAdapter != null) {
                mAdapter!!.clear()
                loadHistory()
                swipe_layout.isRefreshing = false;
            }
        })
    }

    private fun loadHistory() {
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
        historyViewModel!!.getTranscationHistory().observe(this, Observer { historyResponse ->
            if (historyResponse != null) {
                if (historyResponse.innerMsg.history[0].transactionsHistory.isNotEmpty()) {
                    initAdapter(historyResponse)
                } else {
                    tv_no_history.visibility = View.VISIBLE
                }
                shimmer_view_container.stopShimmer()
                shimmer_view_container.visibility = View.GONE
            } else {
                shimmer_view_container.stopShimmer()
                shimmer_view_container.visibility = View.GONE
            }
        })
    }

    private fun initAdapter(data: HistoryApiResponseModel) {
        mAdapter = TransactionHistoryAdapter(activity?.applicationContext!!)
        mAdapter!!.setItems(data.innerMsg.history[0].transactionsHistory)
        mAdapter!!.setListener(this)
        rv_tran_history.adapter = mAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ToolBarControll) {
            toolBarControll = context as ToolBarControll?
        } else {
            throw IllegalArgumentException("Containing activity must implement ToolBarControll interface")
        }
    }

}
