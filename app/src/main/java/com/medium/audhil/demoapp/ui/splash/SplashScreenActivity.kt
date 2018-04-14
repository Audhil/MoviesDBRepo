package com.medium.audhil.demoapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.TextView
import com.medium.audhil.demoapp.ui.base.BaseActivity
import com.medium.audhil.demoapp.ui.dashboard.DashBoardActivity

/*
 * Created by mohammed-2284 on 11/04/18.
 */

class SplashScreenActivity : BaseActivity() {

    private var textView: TextView? = null
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(applicationContext)
        textView?.text = "Splash Screen"
        textView?.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        textView?.textSize = 20f
        setContentView(textView)
    }

    override fun onResume() {
        super.onResume()
        //  with fake delay
        handler.postDelayed({
            launchDashBoardActivity()
        }, 1000)
    }

    //  proceed into app
    private fun launchDashBoardActivity() =
            intent?.action?.let {
                startActivity(DashBoardActivity.newIntent())
                finish()
            }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }
}