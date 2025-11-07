package br.com.littlepig.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.littlepig.R
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.databinding.HomePageFragmentBinding
import br.com.littlepig.presentation.adapter.BalanceAdapter
import br.com.littlepig.presentation.adapter.TransactionAdapter
import br.com.littlepig.presentation.main.MainActivity
import br.com.littlepig.presentation.ui.home.viewmodel.HomeViewModel
import br.com.littlepig.presentation.ui.home.viewmodel.HomeViewModel.UIState
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
    private val balanceAdapter by lazy {
        BalanceAdapter()
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
        configureToolbar()
        setRecyclerViews()
        setCalendarListener()
        updateUIBalance()
        updateUITransactions()
    }

    override fun onResume() {
        super.onResume()
        loadCurrentBalance() // TODO regra para nao recarregar sempre
        loadCurrentDayTransactions() // TODO regra para nao recarregar sempre
    }

    private fun configureToolbar() {
        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
    }

    private fun setRecyclerViews() = with(binding) {
        recycler.run {
            adapter = balanceAdapter
            setHasFixedSize(true)
        }

        recyclerTransactions.run {
            adapter = transactionAdapter
            setHasFixedSize(true)
        }
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
                    it?.let { getTransactions(it) } // TODO corrigir bug: nao att em novas datas
                }
            }

        datePicker.show(parentFragmentManager, TAG_DATE_PICKER)
    }

    private fun getTransactions(date: Long) = viewModel.loadTransactions(date)

    private fun loadCurrentBalance() {
        viewModel.loadBalance(getCurrentDate())
    }

    private fun loadCurrentDayTransactions() {
        viewModel.loadTransactions(getCurrentDate())
    }

    private fun getCurrentDate(): Long = System.currentTimeMillis()

    private fun updateUITransactions() {
        viewModel.transactions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success<*> -> {
                    Log.d("log", "transactionsAdapter: ${state.data}")
                    @Suppress("UNCHECKED_CAST")
                    transactionAdapter.submitList(state.data as? List<UserBalanceResponseItem>) //TODO consultar melhoria para nao usar cast
                }

                is UIState.Empty -> {
                    context?.showToast(state.message)
                }

                is UIState.Error -> {
                    context?.showToast(state.error)
                }

                UIState.NotAuthenticated -> {
                    findNavController().navigate(
                        HomePageFragmentDirections
                            .navigateToLoginPageFragment()
                    )
                }

                UIState.TokenExpired -> {
                    findNavController().navigate(
                        HomePageFragmentDirections
                            .navigateToLoginPageFragment()
                    )
                }
            }
        }
    }

    private fun updateUIBalance() {
        viewModel.balance.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success<*> -> {
                    Log.d("log", "balanceAdapter: ${state.data}")
                    @Suppress("UNCHECKED_CAST")
                    balanceAdapter.submitList(state.data as? List<UserBalanceResponseItem>)//TODO consultar melhoria para nao usar cast
                }

                is UIState.Empty -> {
                    context?.showToast(state.message)
                }

                is UIState.Error -> {
                    context?.showToast(state.error)
                }

                UIState.NotAuthenticated -> {
                    findNavController().navigate(
                        HomePageFragmentDirections
                            .navigateToLoginPageFragment()
                    )
                }

                UIState.TokenExpired -> {
                    findNavController().navigate(
                        HomePageFragmentDirections
                            .navigateToLoginPageFragment()
                    )
                }
            }
        }
    }

    private companion object {
        const val SELECT_DATE = "Selecione a data"
        const val TAG_DATE_PICKER = "DATE_PICKER"
    }
}
