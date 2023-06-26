package io.drdroid.kotlin_app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import io.drdroid.kotlin_app.base.BaseActivity
import io.drdroid.kotlin_app.databinding.ActivityLoginBinding


class Login : BaseActivity() {
    //    private lateinit var mainView: View
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = resources.getColor(android.R.color.transparent, null)
        window.navigationBarColor = resources.getColor(android.R.color.transparent, null)
//        var flags = window.decorView.systemUiVisibility
//        flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
//        window.decorView.systemUiVisibility = flags

//        mainView = layoutInflater.inflate(R.layout.activity_login, null)
//        setContentView(mainView)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val usernameInput: EditText = findViewById(R.id.username)
        val usernameInput: EditText = binding.username
        val passwordInput: EditText = binding.password
        val loginBtn: AppCompatButton = binding.loginBtn



        loginBtn.setOnClickListener {
            val username: String = usernameInput.text.toString()
            val password: String = passwordInput.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@Login,
                    "Please, check your input and try again",
                    Toast.LENGTH_LONG
                )
                    .show()
                return@setOnClickListener
            }
            if (username.length < 3 || password.length < 6) {
                Toast.makeText(this, "Username and/or password is too short", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (!username.isValidEmail()) {
                Toast.makeText(this, "Please, check your email and try again.", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            //go to next activity
            startActivity(Intent(this@Login, Quiz::class.java))
            this@Login.finish()
        }
    }

    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}