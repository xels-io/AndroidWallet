package io.xels.xelsandroidapp.ulits

import android.app.Application

class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        PreferenceManager.setInstance(this)

    }


}