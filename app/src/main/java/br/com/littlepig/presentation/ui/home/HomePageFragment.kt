package br.com.littlepig.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.littlepig.databinding.HomePageFragmentBinding
import br.com.littlepig.presentation.adapter.TransactionAdapter
import br.com.littlepig.presentation.ui.home.viewmodel.HomeViewModel
import br.com.littlepig.utils.showToast
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private val binding: HomePageFragmentBinding by lazy {
        HomePageFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()
    private val transactionAdapter by lazy {
        TransactionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerTransactionsView()
        setCalendarListener()
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTransactions(getCurrentDate())
    }

    private fun setCalendarListener() {
        binding.calendar.setOnClickListener {
            showCalendar()
        }
    }

    private fun showCalendar() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(SELECT_DATE)
            .build().apply {
                addOnPositiveButtonClickListener {
                    it?.let { getTransactions(it) }
                }
            }

        datePicker.show(parentFragmentManager, TAG_DATE_PICKER)
    }

    private fun getTransactions(date: Long) = viewModel.loadTransactions(date)

    private fun updateUI() {
        viewModel.transactions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeViewModel.TransactionState.Success -> {
                    transactionAdapter.submitList(state.transactions)
                }

                is HomeViewModel.TransactionState.Empty -> {
                    Log.d("gabriel", "empty")
                    context?.showToast(state.message)
                }

                is HomeViewModel.TransactionState.Error -> {
                    context?.showToast(state.error)
                }

                HomeViewModel.TransactionState.NotAuthenticated -> {
                    Log.d("gabriel", "nao autenticado")
                }

                HomeViewModel.TransactionState.TokenExpired -> {
                    Log.d("gabriel", "token expirado")
                }
            }
        }
    }

    private fun getCurrentDate(): Long = System.currentTimeMillis()

    private fun setRecyclerTransactionsView() = with(binding) {
        recyclerTransactions.run {
            adapter = transactionAdapter
            setHasFixedSize(true)
        }
    }

    private companion object {
        const val SELECT_DATE = "Selecione a data"
        const val TAG_DATE_PICKER = "DATE_PICKER"
    }
}
