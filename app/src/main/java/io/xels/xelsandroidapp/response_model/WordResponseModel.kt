package io.xels.xelsandroidapp.response_model
import com.google.gson.annotations.SerializedName


data class WordResponseModel(
    @SerializedName("InnerMsg")
    val innerMsg: String, // apart sunset thumb seek wealth hint cheese organ uniform memory car minimum
    @SerializedName("statusCode")
    val statusCode: Int, // 200
    @SerializedName("statusText")
    val statusText: String // OK
)