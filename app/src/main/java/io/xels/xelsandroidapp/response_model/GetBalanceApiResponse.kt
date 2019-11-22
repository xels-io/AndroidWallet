package io.xels.xelsandroidapp.response_model
import com.google.gson.annotations.SerializedName


  data class GetBalanceApiResponse(
    @SerializedName("statusCode")
    var statusCode: Int, // 200
    @SerializedName("statusText")
    var statusText: String, // OK
    @SerializedName("InnerMsg")
    var innerMsg: InnerMsg
) {
    data class InnerMsg(
        @SerializedName("balances")
        var balances: List<Balance>
    ) {
        data class Balance(
            @SerializedName("accountName")
            var accountName: String, // account 0
            @SerializedName("accountHdPath")
            var accountHdPath: String, // m/44'/105'/0'
            @SerializedName("coinType")
            var coinType: Int, // 105
            @SerializedName("amountConfirmed")
            var amountConfirmed: Long, // 131734215700060000
            @SerializedName("amountUnconfirmed")
            var amountUnconfirmed: Long // 0
        )
    }
}