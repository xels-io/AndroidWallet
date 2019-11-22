package io.xels.xelsandroidapp.request_model
import com.google.gson.annotations.SerializedName


data class RecipientModel(
    @SerializedName("destinationAddress")
    var destinationAddress: String, // XRu1RwzpNhY1ZJfaTJNyknJXu1sMzPNaav
    @SerializedName("amount")
    var amount: String // 1000
)