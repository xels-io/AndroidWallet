package io.xels.xelsandroidapp.response_model

import com.google.gson.annotations.SerializedName

data class LoadApiResponseModel(
    @SerializedName("statusCode")
    var statusCode: Int, // 200
    @SerializedName("statusText")
    var statusText: String, // OK
    @SerializedName("InnerMsg")
    var innerMsg: String
)