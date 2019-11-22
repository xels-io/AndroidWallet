package io.xels.xelsandroidapp.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import io.xels.xelsandroidapp.R
import io.xels.xelsandroidapp.ulits.AppConstance
import io.xels.xelsandroidapp.ulits.PreferenceManager


//Mashfi changes

class SplashScreenActivity : Activity() {
    internal var thread: Thread?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        PreferenceManager.setInstance(this)

        thread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(AppConstance.SPLASH_SCREEN_TIMER)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {

                        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()


                }
            }
        }
        (thread as Thread).start()
    }
}
