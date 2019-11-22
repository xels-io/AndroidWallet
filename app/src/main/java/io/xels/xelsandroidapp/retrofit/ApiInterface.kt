package io.xels.xelsandroidapp.retrofit

import io.xels.xelsandroidapp.model.TransactionConfirmedResponse
import io.xels.xelsandroidapp.response_model.*
import io.xels.xelsandroidapp.ulits.AppConstance
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST(AppConstance.postAPIResponse)
    fun loadApiCall(@Query("URL") url: String, @Query("name") name: String, @Query("password") password: String): Call<LoadApiResponseModel>

    @POST(AppConstance.postAPIResponse)
    fun sendTrasactionApi(@Query("URL") url: String, @Query("Hex") name: String): Call<SendTrasectionApiResponse>

    @POST(AppConstance.postAPIResponse)
    fun restoreWalletApi(
        @Query("URL") url: String, @Query("creationDate") date: String, @Query("mnemonic") mnemonic: String, @Query(
            "name"
        ) name: String,
        @Query(
            "passPhrase"
        ) passPhrase: String, @Query("password")password: String
    ): Call<LoadApiResponseModel>

    @POST(AppConstance.postAPIResponse)
    fun makeTransaction(
        @Query("URL") url: String, @Query("walletName") name: String, @Query("accountName") accName: String,
        @Query("recipients[0].[destinationAddress]") address: String, @Query("recipients[0].[amount]") amount: String, @Query(
            "feeAmount"
        ) feeAmount: String, @Query(
            "allowUnconfirmed"
        ) allowUn: Boolean, @Query(
            "password"
        ) password: String, @Query("shuffleOutputs") shuffleOutputs: Boolean
    ): Call<TransectionApiResponse>


    @POST(AppConstance.postAPIResponse)
    fun makeNewTransaction(
        @Query("URL") url: String, @Query("walletName") name: String, @Query("accountName") accName: String,
        @Query("recipients") recipients: String, @Query(
            "feeAmount"
        ) feeAmount: String, @Query(
            "allowUnconfirmed"
        ) allowUn: Boolean, @Query(
            "password"
        ) password: String, @Query("shuffleOutputs") shuffleOutputs: Boolean
    ): Call<TransectionApiResponse>


    @POST(AppConstance.postAPIResponse)
    fun createWallet(
        @Query("URL") url: String, @Query("folderPath") folderPath: String?, @Query("mnemonic") mnemonic: String?, @Query(
            "name"
        ) name: String?,
        @Query("password") password: String?, @Query("passphrase") passphrase: String?
    ): Call<WordResponseModel>


    @GET(AppConstance.getAPIResponse)
    fun nodeStatusApi(): Call<NodeStatusApiResponse?>

    @GET(AppConstance.getAPIResponse)
    fun getBalance(@Query("URL") url: String, @Query("walletName") walletName: String, @Query("accountName") accName: String): Call<GetBalanceApiResponse?>

    @GET(AppConstance.getAPIResponse)
    fun getAllAddress(@Query("URL") url: String, @Query("walletName") walletName: String, @Query("accountName") accName: String): Call<AllAddressApiResponse?>


    @GET(AppConstance.getAPIResponse)
    fun getHistory(@Query("URL") url: String, @Query("walletName") walletName: String, @Query("accountName") accName: String): Call<HistoryApiResponseModel?>

    @GET(AppConstance.getAPIResponse)
    fun getStackingInfo(@Query("URL") url: String): Call<GetStakingInfoResponse?>

    @GET(AppConstance.getAPIResponse)
    fun getUnUsedAddress(@Query("URL") url: String, @Query("walletName") walletName: String, @Query("accountName") accName: String): Call<GetUnUsedAddressResponseModel?>

    @GET(AppConstance.getAPIResponse)
    fun getMnemonics(@Query("URL") url: String, @Query("language") language: String, @Query("wordCount") wordCount: Int): Call<WordResponseModel?>

    @GET(AppConstance.getAPIResponse)
    fun getEstimateTax(
        @Query("URL") url: String, @Query("walletName") walletName: String, @Query("accountName") accName: String, @Query(
            "recipients[0].[destinationAddress]"
        ) destinationAddress: String, @Query("recipients[0].[amount]") amount: String, @Query(
            "feeType"
        ) type: String, @Query("allowUnconfirmed") allowUn: Boolean
    ): Call<FeeResponseApi>

    @POST(AppConstance.postAPIResponse)
    fun getNewEstimateTax(
        @Query("URL") url: String, @Query("walletName") walletName: String, @Query("accountName") accName: String, @Query("recipients") recipients: String , @Query(
            "feeType"
        ) type: String, @Query("allowUnconfirmed") allowUn: Boolean
    ): Call<FeeResponseApi>

    @GET(AppConstance.getAPIResponse)
    fun getGeneralInfo(
        @Query("URL") url: String, @Query("Name") walletName: String
    ) : Call<TransactionConfirmedResponse?>




}