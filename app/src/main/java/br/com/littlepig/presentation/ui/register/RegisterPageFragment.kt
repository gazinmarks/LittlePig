package br.com.littlepig.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.littlepig.R
import br.com.littlepig.databinding.RegisterPageFragmentBinding
import br.com.littlepig.presentation.main.MainActivity
import br.com.littlepig.presentation.ui.register.viewmodel.RegisterViewModel
import br.com.littlepig.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPageFragment : Fragment() {
    private val binding: RegisterPageFragmentBinding by lazy {
        RegisterPageFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        setListeners()
        createAccount()
        updateUI()
    }

    private fun configureToolbar() {
        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    }

    private fun setListeners() = with(binding) {
        iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun createAccount() = with(binding) {
        buttonLogin.setOnClickListener {
            val name = fieldUsername.text.toString()
            val password = fieldPassword.text.toString()
            val email = fieldEmail.text.toString()

            val fields: List<String> = listOf(name, password, email)

            viewModel.handleUser(fields)
        }
    }

    private fun updateUI() {
        viewModel.user.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegisterViewModel.State.Success -> {
                    context?.showToast("Usuario registrado com sucesso")
                    navigateToLogin()
                }

                is RegisterViewModel.State.Failure -> {
                    context?.showToast("${state.error}")
                }

                is RegisterViewModel.State.Loading -> {
                    // do nothing
                }
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            RegisterPageFragmentDirections.navigateToLoginPageFragment()
        )
    }
}
