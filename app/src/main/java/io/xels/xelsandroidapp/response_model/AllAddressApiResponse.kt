package io.xels.xelsandroidapp.response_model

import com.google.gson.annotations.SerializedName

 data class AllAddressApiResponse(
    @SerializedName("InnerMsg")
    val innerMsg: InnerMsg,
    @SerializedName("statusCode")
    val statusCode: Int, // 200
    @SerializedName("statusText")
    val statusText: String // OK
) {
    data class InnerMsg(
        @SerializedName("addresses")
        val addresses: List<Addresse>
    ) {
        data class Addresse(
            @SerializedName("address")
            val address: String, // XJNjCn47C6kJjvzyxr9T7ouUgWC3Z78ovj
            @SerializedName("isChange")
            val isChange: Boolean, // true
            @SerializedName("isUsed")
            val isUsed: Boolean // false
        )
    }
}