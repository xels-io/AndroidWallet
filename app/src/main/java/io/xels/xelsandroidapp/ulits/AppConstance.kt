package io.xels.xelsandroidapp.ulits

import android.net.ConnectivityManager
import java.math.BigDecimal

class AppConstance {

    companion object {
        /*
                val STABLE_BASE_URL: String = "http://13.115.56.41:4000/"
        */
        val BASE_URL: String = "https://api.xels.io:2332"
        val ACCOUNT_NAME: String = "account 0"

        const val postAPIResponse: String = "PostAPIResponse"
        const val getAPIResponse: String = "GetAPIResponse"
        const val getBalance: String = "/api/wallet/balance"
        val load: String = "/api/wallet/load"
        val confirmation: String = "/api/wallet/general-info"
        val nodeStatus: String = "/api/node/status"
        val getHistory: String = "/api/wallet/history"
        val getAddress: String = "/api/wallet/addresses"
        val getStackingInfo: String = "/api/staking/getstakinginfo"
        val createWallet: String = "/api/wallet/create/ "
        val estimateTax: String = "/api/wallet/estimate-txfee"
        val transection: String = "/api/wallet/build-transaction"
        val unAddress: String = "/api/wallet/unusedaddress"
        val sendTransection: String = "/api/wallet/send-transaction"
        val recoverWallet: String = "/api/wallet/recover/"
        const val mnemonicsUrl: String = "/api/wallet/mnemonic"
        val SHARED_PREFERENCE = "io.xels.xelsandroidapp"
        val walletName = "walletName"
        val isLogin: String = "isLogin"
        val balance: String = "balance"
        val password: String = "PASSWORD"
        val shatoshi: Double = 100000000.0
        val SPLASH_SCREEN_TIMER: Long = 1000
        val keepLogin: String = "keepLogin"
        var typeNetwork: IntArray = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
        var baseUrl: String = "BASE_URL"

    }


}