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
import io.xels.xelsandroidapp.adapter.StackedListAdapter
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.view_model.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_stacked.*

class StackedFragment : Fragment() {


    private var toolBarControll: ToolBarControll? = null
    var apiInterface: ApiInterface? = null
    lateinit var noData: TextView
    lateinit var stackedAdapet: StackedListAdapter
    private var historyViewModel: HistoryViewModel? = null
    private var type: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        type = this.arguments?.getInt("type", 0)!!

        if (type == 0) {
            toolBarControll?.setTitle(getString(R.string.hybrid_reward))

        } else
            if (type == 1) {
                toolBarControll?.setTitle(getString(R.string.pow_reward))

            }

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


                    if (type == 0) {

                        for (i in historyResponse.innerMsg.history[0].transactionsHistory) {

                            if (i.type.equals("staked"))

                            {
                                stackedAdapet = StackedListAdapter(historyResponse, type)

                                stacked_rv.setAdapter(stackedAdapet)
                            }

                            else {
                                noData.visibility = View.VISIBLE

                            }
                        }




                    } else if (type == 1) {
                        for (i in historyResponse.innerMsg.history[0].transactionsHistory){


                            if (i.type.equals("Mined")
                            ) {
                                stackedAdapet = StackedListAdapter(historyResponse, type)

                                stacked_rv.setAdapter(stackedAdapet)
                            } else {
                                noData.visibility = View.VISIBLE

                            }

                        }

                    }


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

