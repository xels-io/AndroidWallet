package io.xels.xelsandroidapp.response_model

import com.google.gson.annotations.SerializedName

 data class ErrorApiResponse(
    @SerializedName("InnerMsg")
    val innerMsg: List<InnerMsg>,
    @SerializedName("statusCode")
    val statusCode: Int, // 403
    @SerializedName("statusText")
    val statusText: String // Forbidden
) {
    data class InnerMsg(
        @SerializedName("description")
        val description: String, // System.Security.SecurityException: Invalid password (or invalid Network)   at Xels.Bitcoin.Features.Wallet.WalletManager.LoadWallet(String password, String name)   at Xels.Bitcoin.Features.Wallet.Controllers.WalletController.Load(WalletLoadRequest request)
        @SerializedName("message")
        val message: String, // Wrong password, please try again.
        @SerializedName("status")
        val status: Int // 403
    )
}