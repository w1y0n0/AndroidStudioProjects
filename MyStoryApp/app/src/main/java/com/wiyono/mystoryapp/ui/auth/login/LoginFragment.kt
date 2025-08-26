package com.wiyono.mystoryapp.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.wiyono.mystoryapp.R
import com.wiyono.mystoryapp.data.domain.Result
import com.wiyono.mystoryapp.databinding.FragmentLoginBinding
import com.wiyono.mystoryapp.ui.main.MainActivity
import com.wiyono.mystoryapp.viewmodel.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this, ViewModelFactory(requireActivity()))[LoginViewModel::class.java]

        binding?.btnRegister?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.edLoginEmail?.text.toString().trim()
            val password = binding?.edPasswordLogin?.text.toString().trim()

            if (!isFormValid(email, password)) {
                Toast.makeText(requireActivity(), getString(R.string.form_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(email, password).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> { showLoading(true) }
                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(requireActivity(), "Selamat Datang, " + result.data.loginResult!!.name, Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(requireActivity(), result.error + ": Email/Password salah.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        playAnimation()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding?.ivLoginLogo, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding?.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding?.edPasswordLogin, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding?.btnLogin, View.ALPHA, 1f).setDuration(500)
        val stroke = ObjectAnimator.ofFloat(binding?.view, View.ALPHA, 1f).setDuration(500)
        val move = ObjectAnimator.ofFloat(binding?.tvRegister, View.ALPHA, 1f).setDuration(500)
        val regis = ObjectAnimator.ofFloat(binding?.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, email, password, login, stroke, move, regis)
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isFormValid(email: String, password: String): Boolean {
        val isEmailValid = email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 8

        return isEmailValid && isPasswordValid
    }

    private fun showLoading(isLoading: Boolean) { binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE }
}