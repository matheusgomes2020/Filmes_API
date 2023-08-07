package com.example.filmes.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import com.example.filmes.MainActivity
import com.example.filmes.databinding.ActivityLoginBinding
import com.example.filmes.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private var email = ""
    private var password = ""

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.user.collect {
                if (it.isLoading) {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_SHORT).show()

                }
                it.data?.let {
                   binding.progressCircular.visibility = View.GONE
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
        }


        binding.btnSignIn.setOnClickListener {
            with(binding) {
                email = edtEmailID.text.toString()
                password = edtPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(email, password)
                } else {
                    Toast.makeText(this@LoginActivity, "Email and Password Must be Entered.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            with(binding) {
                email = edtEmailID.text.toString()
                password = edtPassword.text.toString()

                val user = User(
                    name = "Jahid Hasan",
                    image = "",
                    email = email,
                    active = true,
                    address = "Dhaka, Bangladesh"
                )

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.register(email, password, user)
                } else {
                    Toast.makeText(this@LoginActivity, "Email and Password Must be Entered.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}