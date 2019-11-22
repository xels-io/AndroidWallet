package io.xels.xelsandroidapp.model

import com.google.gson.annotations.SerializedName

data class TransactionConfirmedResponse(
    @SerializedName("InnerMsg")
    val innerMsg: InnerMsg,
    @SerializedName("statusCode")
    val statusCode: Int, // 200
    @SerializedName("statusText")
    val statusText: String // OK
) {
    data class InnerMsg(
        @SerializedName("chainTip")
        val chainTip: Int, // 18794
        @SerializedName("connectedNodes")
        val connectedNodes: Int, // 6
        @SerializedName("creationTime")
        val creationTime: String, // 1551398400
        @SerializedName("isChainSynced")
        val isChainSynced: Boolean, // true
        @SerializedName("isDecrypted")
        val isDecrypted: Boolean, // true
        @SerializedName("lastBlockSyncedHeight")
        val lastBlockSyncedHeight: Int, // 18794
        @SerializedName("network")
        val network: String, // XelsMain
        @SerializedName("walletFilePath")
        val walletFilePath: String // C:\Users\Administrator\AppData\Roaming\XelsNode\xels\XelsMain\Neo.wallet.json
    )
}