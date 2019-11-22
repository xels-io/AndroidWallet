package io.xels.xelsandroidapp.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


        var gson: Gson? = GsonBuilder()
            .setLenient().create()


        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {

            if (retrofit == null) {
                retrofit =
                        Retrofit.Builder().baseUrl(PreferenceManager.getString(AppConstance.baseUrl))
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
            }

            return retrofit
        }


    fun destroyInstance(){
         retrofit = null
    }
    }