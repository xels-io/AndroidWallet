package io.xels.xelsandroidapp.response_model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class NodeStatusApiResponse(
    @SerializedName("agent")
    var agent: String, // XelsNode
    @SerializedName("version")
    var version: String, // 1.2.6.0
    @SerializedName("network")
    var network: String, // XelsMain
    @SerializedName("coinTicker")
    var coinTicker: String, // XEL
    @SerializedName("processId")
    var processId: Int, // 22796
    @SerializedName("consensusHeight")
    var consensusHeight: Int, // 775
    @SerializedName("blockStoreHeight")
    var blockStoreHeight: Int, // 775
    @SerializedName("inboundPeers")
    var inboundPeers: List<Any>,
    @SerializedName("outboundPeers")
    var outboundPeers: List<Any>,
    @SerializedName("enabledFeatures")
    var enabledFeatures: List<String>,
    @SerializedName("dataDirectoryPath")
    var dataDirectoryPath: String, // C:\Users\ASUS\AppData\Roaming\XelsNode\xels\XelsMain
    @SerializedName("runningTime")
    var runningTime: String, // 00:14:16.0769232
    @SerializedName("difficulty")
    var difficulty: Double, // 110676.22944905427
    @SerializedName("protocolVersion")
    var protocolVersion: Int, // 70012
    @SerializedName("testnet")
    var testnet: Boolean, // false
    @SerializedName("relayFee")
    var relayFee: Double // 0.00010000


) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) },
        ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) },
        source.createStringArrayList(),
        source.readString(),
        source.readString(),
        source.readDouble(),
        source.readInt(),
        1 == source.readInt(),
        source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(agent)
        writeString(version)
        writeString(network)
        writeString(coinTicker)
        writeInt(processId)
        writeInt(consensusHeight)
        writeInt(blockStoreHeight)
        writeList(inboundPeers)
        writeList(outboundPeers)
        writeStringList(enabledFeatures)
        writeString(dataDirectoryPath)
        writeString(runningTime)
        writeDouble(difficulty)
        writeInt(protocolVersion)
        writeInt((if (testnet) 1 else 0))
        writeDouble(relayFee)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<NodeStatusApiResponse> = object : Parcelable.Creator<NodeStatusApiResponse> {
            override fun createFromParcel(source: Parcel): NodeStatusApiResponse = NodeStatusApiResponse(source)
            override fun newArray(size: Int): Array<NodeStatusApiResponse?> = arrayOfNulls(size)
        }
    }
}