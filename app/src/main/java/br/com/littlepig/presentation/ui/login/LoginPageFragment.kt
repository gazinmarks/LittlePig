package br.com.littlepig.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.littlepig.R
import br.com.littlepig.databinding.LoginPageFragmentBinding

class LoginPageFragment : Fragment() {
    private val binding: LoginPageFragmentBinding by lazy {
        LoginPageFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() = with(binding) {
        val navController = findNavController()

        createAccountText.setOnClickListener {
            if (navController.currentDestination?.id == R.id.loginPageFragment) {
                navController.navigate(R.id.registerPageFragment)
            }
        }
    }
}
