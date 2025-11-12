package br.com.littlepig.presentation.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.littlepig.databinding.CreateTransactionFragmentBinding
import br.com.littlepig.presentation.ui.transactions.viewmodel.CreateTransactionViewModel
import br.com.littlepig.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class CreateTransactionPageFragment : Fragment() {
    private val binding: CreateTransactionFragmentBinding by lazy {
        CreateTransactionFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: CreateTransactionViewModel by viewModels()

    override fun onCreateView( // TODO alterar title da toolbar
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListenerChecked()
        configureButtonCreateTransaction()
        updateUI()
    }

    private fun setButtonListenerChecked() = with(binding) {
        receiptButton.addOnCheckedChangeListener { button, isChecked ->
            expenseButton.isChecked = false
            button.isChecked = isChecked
        }
        expenseButton.addOnCheckedChangeListener { button, isChecked ->
            receiptButton.isChecked = false
            button.isChecked = isChecked
        }
    }

    private fun configureButtonCreateTransaction() = with(binding) {
        createTransactionButton.setOnClickListener {
            createNewTransaction()
        }
    }

    private fun createNewTransaction() = with(binding) {
        val description = nameTransactionField.text.toString()
        val value = valueTransactionField.text.toString()
        val type = if (receiptButton.isChecked) {
            receiptButton.text
        } else {
            expenseButton.text
        }.toString().lowercase()
        val date = System.currentTimeMillis()

        viewModel.createTransaction(description, value, type, date)
    }

    private fun updateUI() {
        viewModel.newTransaction.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CreateTransactionViewModel.State.Success<*> -> {
                    context?.showToast("Criado com sucesso!")
                    goToHomePage()
                }

                is CreateTransactionViewModel.State.Error -> {
                    context?.showToast(state.message.asString(requireContext()))
                }

                is CreateTransactionViewModel.State.ValueEmpty -> TODO()
            }
        }
    }

    private fun goToHomePage() {
        findNavController().navigate(
            CreateTransactionPageFragmentDirections
                .navigateToHomePageFragment()
        )
    }
}
