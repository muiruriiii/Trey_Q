package com.android.waterqualitymonitoring
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // get reference to all views
        var username = findViewById(R.id.email) as EditText
        var password = findViewById(R.id.password) as EditText
        var loginButton = findViewById(R.id.loginButton) as Button

        // set on-click listener
        loginButton.setOnClickListener {
            val username = username.text;
            val password = password.text;
            Toast.makeText(this@Login, username, Toast.LENGTH_LONG).show()

            // your code to validate the user_name and password combination
            // and verify the same

        }
    }

}


