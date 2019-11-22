package io.xels.xelsandroidapp.response_model

import com.google.gson.annotations.SerializedName

data class SendTrasectionApiResponse(
    @SerializedName("InnerMsg")
    val innerMsg: InnerMsg,
    @SerializedName("statusCode")
    val statusCode: Int, // 200
    @SerializedName("statusText")
    val statusText: String // OK
) {
    data class InnerMsg(
        @SerializedName("outputs")
        val outputs: List<Output>,
        @SerializedName("transactionId")
        val transactionId: String // ae86dc16217f8a7526536566dffc15c28d472632e0ae4381fffde245e3a3a425
    ) {
        data class Output(
            @SerializedName("address")
            val address: String, // XWi5FzYEgeBekyxtp4xne5EpQZ3uSK3iAr
            @SerializedName("amount")
            val amount: Long // 10000000000
        )
    }
}