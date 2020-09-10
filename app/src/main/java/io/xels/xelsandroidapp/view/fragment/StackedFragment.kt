package io.xels.xelsandroidapp.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.adapter.StackedListAdapter
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.model.ListAdapterData
import io.xels.xelsandroidapp.retrofit.ApiInterface
import io.xels.xelsandroidapp.view_model.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_stacked.*

class StackedFragment : androidx.fragment.app.Fragment() {


    private var toolBarControll: ToolBarControll? = null
    var apiInterface: ApiInterface? = null
    lateinit var noData: TextView
    lateinit var stackedAdapet: StackedListAdapter
    private var historyViewModel: HistoryViewModel? = null
    private var type: Int = 0;
    private var  data= ArrayList<ListAdapterData>()
    lateinit var modelClass:ListAdapterData

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


                        if (historyResponse.innerMsg.history[0].transactionsHistory.size > 0) {
                            for (i in historyResponse.innerMsg.history[0].transactionsHistory) {

                                if (i.type.equals("staked")) {
                                    noData.visibility = View.GONE


                                    modelClass=ListAdapterData(i.amount,i.toAddress,i.type,i.timestamp)

                                    data.add(modelClass)

                                }
                            }

                            stackedAdapet = StackedListAdapter(data, type)
                            stacked_rv.setAdapter(stackedAdapet)

                        } else {
                            noData.text = "You have no hybrid reward"
                            noData.visibility = View.VISIBLE

                        }


                    } else if (type == 1) {

                        if (historyResponse.innerMsg.history[0].transactionsHistory.size>0){

                            for (i in historyResponse.innerMsg.history[0].transactionsHistory) {


                                if (i.type.toLowerCase().equals("mined")
                                ) {
                                    noData.visibility = View.GONE
                                    modelClass=ListAdapterData(i.amount,i.toAddress,i.type,i.timestamp)
                                    data.add(modelClass)
                                }



                            }

                            stackedAdapet = StackedListAdapter(data, type)
                            stacked_rv.setAdapter(stackedAdapet)




                        }
                        else {
                            noData.text = "You have no pow reward"
                            noData.visibility = View.VISIBLE

                        }

                    }


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
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        stacked_rv.setLayoutManager(mLayoutManager)
        stacked_rv.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
        stacked_rv.setHasFixedSize(true)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ToolBarControll) toolBarControll = context else {
            throw IllegalArgumentException("Containing activity must implement OnSearchListener interface")
        }
    }

}


