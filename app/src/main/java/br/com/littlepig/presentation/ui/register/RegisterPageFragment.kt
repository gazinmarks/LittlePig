package br.com.littlepig.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.littlepig.presentation.main.MainActivity
import br.com.littlepig.R
import br.com.littlepig.databinding.RegisterPageFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterPageFragment : Fragment() {
    private val binding: RegisterPageFragmentBinding by lazy {
        RegisterPageFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
        setListeners()
    }

    private fun configureToolbar() {
        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
    }

    private fun setListeners() = with(binding) {
        iconBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
