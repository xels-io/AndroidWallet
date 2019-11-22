package io.xels.xelsandroidapp.response_model
import com.google.gson.annotations.SerializedName


data class FeeResponseApi(
    @SerializedName("InnerMsg")
    val innerMsg: Long, // 18580
    @SerializedName("statusCode")
    val statusCode: Int, // 200
    @SerializedName("statusText")
    val statusText: String // OK
)