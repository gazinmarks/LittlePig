package br.com.littlepig.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.littlepig.R
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.databinding.CardViewBalanceBinding
import br.com.littlepig.utils.formatCurrency

class BalanceAdapter :
    ListAdapter<UserBalanceResponseItem, BalanceAdapter.BalanceAdapterViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = CardViewBalanceBinding.inflate(inflater, parent, false)

        return BalanceAdapterViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BalanceAdapterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BalanceAdapterViewHolder(
        private val binding: CardViewBalanceBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(balance: UserBalanceResponseItem) = with(binding) {
            when (balance.tag) {
                CURRENT_BALANCE -> {
                    titleBalance.text = getString(context, R.string.current_balance)
                    containerBalance.setBackgroundColor(getColor(context, R.color.blue))
                }

                CURRENT_RECEIPT -> {
                    titleBalance.text = getString(context, R.string.today_entries)
                    containerBalance.setBackgroundColor(getColor(context, R.color.green))
                }

                CURRENT_EXPENSE -> {
                    titleBalance.text = getString(context, R.string.today_outings)
                    containerBalance.setBackgroundColor(getColor(context, R.color.red))
                }
            }
            valueBalance.text = balance.saldo.formatCurrency()
        }
    }

    private companion object {
        const val CURRENT_BALANCE = "saldo"
        const val CURRENT_RECEIPT = "receita"
        const val CURRENT_EXPENSE = "despesa"
    }
}

object DiffCallback : DiffUtil.ItemCallback<UserBalanceResponseItem>() {
    override fun areItemsTheSame(
        oldItem: UserBalanceResponseItem,
        newItem: UserBalanceResponseItem
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: UserBalanceResponseItem,
        newItem: UserBalanceResponseItem
    ): Boolean = oldItem == newItem
}
