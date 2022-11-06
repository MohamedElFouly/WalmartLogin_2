package edu.miu.walmartlogin.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.miu.walmartlogin.data.User
import edu.miu.walmartlogin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val users = mutableListOf(
        User("first_1", "last_1", "email_1@gmail.com", "password_1"),
        User("first_2", "last_2", "email_2@gmail.com", "password_2"),
        User("first_3", "last_3", "email_3@gmail.com", "password_3"),
        User("first_4", "last_4", "email_4@gmail.com", "password_4"),
        User("first_5", "last_5", "email_5@gmail.com", "password_5")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user: User? = intent.getSerializableExtra("user") as User?
        user?.let { users.add(it) }
    }

    fun onSign(view: View) {
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString().trim()
        if(email.isEmpty()){
            Toast.makeText(applicationContext,"Please enter your email", Toast.LENGTH_LONG).show()
            return
        }
        if(pass.isEmpty()){
            Toast.makeText(applicationContext,"Please enter your password", Toast.LENGTH_LONG).show()
            return
        }
        var isUserFound = false
        for(user in users){
            if(user.username == email && user.password == pass){
                isUserFound = true
                openShoppingCategoryActivity(user)
            }
        }
        if(!isUserFound){
            Toast.makeText(applicationContext,"Username and password not found.", Toast.LENGTH_LONG).show()
        }
    }

    private fun openShoppingCategoryActivity(user: User){
        val intent = Intent(this, ShoppingCategoryActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    fun onSignUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun onForgetPasswordClick(view: View) {
        val email = binding.etEmail.text.toString().trim()
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Forgot Password to Walmart")
        intent.putExtra(Intent.EXTRA_TEXT, "Forgot Password to Walmart " +
                "for resetting use this code: 123456.");
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}