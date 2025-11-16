package br.com.littlepig.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.littlepig.databinding.RegisterPageFragmentBinding
import br.com.littlepig.presentation.ui.UiText
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
        setListeners()
        createAccount()
        updateUI()
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
                is RegisterViewModel.State.Success<*> -> {
                    context?.showToast(
                        (state.data as UiText.DynamicResource).asString(requireContext())
                    )
                    navigateToLogin()
                }

                is RegisterViewModel.State.Failure -> {
                    context?.showToast(state.error.asString(requireContext()))
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
