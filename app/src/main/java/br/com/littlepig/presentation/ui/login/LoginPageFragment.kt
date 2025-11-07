package br.com.littlepig.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.littlepig.R
import br.com.littlepig.databinding.LoginPageFragmentBinding
import br.com.littlepig.presentation.ui.login.viewmodel.LoginViewModel
import br.com.littlepig.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPageFragment : Fragment() {
    private val binding: LoginPageFragmentBinding by lazy {
        LoginPageFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        getContentFields()
        updateUI()
    }

    private fun setListeners() = with(binding) {
        val navController = findNavController()

        createAccountText.setOnClickListener {
            if (navController.currentDestination?.id == R.id.loginPageFragment) {
                navController.navigate(R.id.registerPageFragment)
            }
        }
    }

    private fun getContentFields() = with(binding) {
        buttonLogin.setOnClickListener {
            val email = fieldEmail.text.toString()
            val password = fieldPassword.text.toString()

            val fields = listOf(email, password)

            viewModel.login(fields)
        }
    }

    private fun updateUI() {
        viewModel.user.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginViewModel.LoginState.Success -> {
                    context?.showToast("Bem vindo!")
                    findNavController().navigate(
                        LoginPageFragmentDirections
                            .navigateToHomePageFragment()
                    )
                }

                is LoginViewModel.LoginState.Failure -> {
                    context?.showToast("${state.exception}")
                }
            }
        }
    }
}
