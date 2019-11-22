package io.xels.xelsandroidapp.response_model

import com.google.gson.annotations.SerializedName

data class GetStakingInfoResponse(
    @SerializedName("statusCode")
    var statusCode: Int, // 200
    @SerializedName("statusText")
    var statusText: String, // OK
    @SerializedName("InnerMsg")
    var innerMsg: InnerMsg
) {
    data class InnerMsg(
        @SerializedName("enabled")
        var enabled: Boolean, // true
        @SerializedName("staking")
        var staking: Boolean, // true
        @SerializedName("errors")
        var errors: Any?, // null
        @SerializedName("currentBlockSize")
        var currentBlockSize: Int, // 150
        @SerializedName("currentBlockTx")
        var currentBlockTx: Int, // 1
        @SerializedName("pooledTx")
        var pooledTx: Int, // 0
        @SerializedName("difficulty")
        var difficulty: Double, // 133504155.06467919
        @SerializedName("searchInterval")
        var searchInterval: Int, // 368
        @SerializedName("weight")
        var weight: Long, // 129072327887655000
        @SerializedName("netStakeWeight")
        var netStakeWeight: Long, // 46616236188013320
        @SerializedName("expectedTime")
        var expectedTime: Int // 204
    )
}