package com.example.filmes.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.filmes.MainActivity
import com.example.filmes.databinding.LoginTabFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginTabFragment: Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var email = ""
    private var password = ""

    private var _binding: LoginTabFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginTabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.user.collect {
                if (it.isLoading) {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()

                }
                it.data?.let {
                    binding.progressCircular.visibility = View.GONE
                    startActivity(Intent(context, MainActivity::class.java))
                }
            }
        }

        binding.button.setOnClickListener {
            with(binding) {
                email = emailLoginTab.text.toString()
                password = pass.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(email, password)
                } else {
                    Toast.makeText(getContext(), "Email and Password Must be Entered.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}