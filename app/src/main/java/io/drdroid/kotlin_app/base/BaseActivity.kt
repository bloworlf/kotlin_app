package io.drdroid.kotlin_app.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        window.statusBarColor = resources.getColor(android.R.color.transparent, null)
//        window.navigationBarColor = resources.getColor(android.R.color.transparent, null)
        var flags = window.decorView.systemUiVisibility
        flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.decorView.systemUiVisibility = flags
    }
}