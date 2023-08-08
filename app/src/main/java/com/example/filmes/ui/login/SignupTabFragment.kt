package com.example.filmes.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.filmes.MainActivity
import com.example.filmes.databinding.SignupTabFragmentBinding
import com.example.filmes.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupTabFragment: Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private var email = ""
    private var password = ""
    private var name = ""
    private var telefone = ""

    private var _binding: SignupTabFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SignupTabFragmentBinding.inflate(inflater, container, false)
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
                name = nameSignupTab.text.toString()
                telefone = telefoneSignupTab.text.toString()

                val user = User(
                    name = name,
                    image = "",
                    email = email,
                    telefone = telefone,
                    active = true,
                    address = ""
                )

                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && telefone.isNotEmpty()) {
                    viewModel.register(email, password, user)
                } else {
                    if (email.isEmpty())
                        Toast.makeText(context, "Digite o email!!!", Toast.LENGTH_SHORT).show()
                    if (password.isEmpty())
                        Toast.makeText(context, "Digite a senha!!!", Toast.LENGTH_SHORT).show()
                    if (name.isEmpty())
                        Toast.makeText(context, "Digite o nome!!!", Toast.LENGTH_SHORT).show()
                    if (telefone.isEmpty())
                        Toast.makeText(context, "Digite o telefone!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

}