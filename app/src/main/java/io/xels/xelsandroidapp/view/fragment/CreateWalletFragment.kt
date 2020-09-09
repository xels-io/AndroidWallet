package io.xels.xelsandroidapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.interfaces.ToolBarControll
import io.xels.xelsandroidapp.ulits.Utils


class CreateWalletFragment : androidx.fragment.app.Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_create_wallet -> {
                if (name?.text.toString().isEmpty()) {
                    makeToast(resources.getString(R.string.text_enter_wallet_name))
                } else if (!Utils.checkWalletNameValidity(name?.text.toString().trim())) {
                    makeToast(resources.getString(R.string.text_wallet_name_mismatch))
                } else if (name?.text.toString().trim().length < 1 || name?.text.toString().trim().length > 24) {
                    makeToast(resources.getString(R.string.text_wallet_name_length_checker))
                } else if (!Utils.checkPassword(password?.text.toString())) {
                    makeToast(resources.getString(R.string.text_password_type_mismatch))
                } else if (!confirmPassword?.text.toString().trim().equals(password?.text.toString().trim())) {
                    makeToast(resources.getString(R.string.text_password_mismatch))
                } else if (passphraseTxtView?.text.toString().isEmpty()) {
                    makeToast(resources.getString(R.string.text_enter_passphrase))
                } else if (name?.text.toString().equals(password?.text.toString())) {
                    makeToast(resources.getString(R.string.text_wallet_name_password_same_warning))
                } else {
                    if (toolBarControll!!.internetCheck(activity)) {
                        bundle = Bundle()
                        bundle.putString("name", name?.text.toString())
                        bundle.putString("pass", password?.text.toString())
                        bundle.putString("passphraseTxtView", passphraseTxtView?.text.toString())
                        fragment = MnemonicsFragment()
                        fragment?.arguments = bundle
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayout, fragment as MnemonicsFragment, "MnemonicsFragment")
                            ?.addToBackStack("MnemonicsFragment")?.commit()
                    }
                }
            }

            R.id.linearLayout -> {
                Utils.hideKeyBoard(activity)
            }
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    var name: EditText? = null
    var passphraseTxtView: EditText? = null
    var password: EditText? = null
    var confirmPassword: EditText? = null
    var createWallet: Button? = null
    var linearLayout: LinearLayout? = null
    lateinit var bundle: Bundle
    var toolBarControll: ToolBarControll? = null

    var fragment: androidx.fragment.app.Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = view.findViewById(R.id.et_name)
        password = view.findViewById(R.id.et_password)
        passphraseTxtView = view.findViewById(R.id.passphraseTxtView)
        confirmPassword = view.findViewById(R.id.confirmPassord)
        createWallet = view.findViewById(R.id.btn_create_wallet)
        linearLayout = view.findViewById(R.id.linearLayout)
        createWallet!!.setOnClickListener(this)
        linearLayout!!.setOnClickListener(this)


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ToolBarControll) {
            toolBarControll = context as ToolBarControll?
        } else {
            throw IllegalArgumentException("Containing activity must implement OnSearchListener interface")
        }
    }


}