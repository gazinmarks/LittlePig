package br.com.littlepig.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.littlepig.R
import br.com.littlepig.data.model.balance.UserBalanceResponseItem
import br.com.littlepig.databinding.CardViewTransactionBinding
import br.com.littlepig.utils.formatCurrency

class TransactionAdapter :
    ListAdapter<UserBalanceResponseItem, TransactionAdapter.TransactionAdapterViewHolder>(
        DiffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TransactionAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = CardViewTransactionBinding.inflate(inflater, parent, false)

        return TransactionAdapterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TransactionAdapterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TransactionAdapterViewHolder(
        private val binding: CardViewTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(balance: UserBalanceResponseItem) = with(binding) {
            typeTransaction.apply {
                when (balance.tag == TAG_RECEIPT) {
                    true -> {
                        setChipBackgroundColorResource(R.color.green)
                        setChipIconResource(R.drawable.ic_arrow_up)
                    }
                    false -> setChipBackgroundColorResource(R.color.red)
                }
                text = balance.tag
            }
            valueTransaction.text = balance.saldo.formatCurrency()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<UserBalanceResponseItem>() {
        override fun areItemsTheSame(
            oldItem: UserBalanceResponseItem, newItem: UserBalanceResponseItem
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: UserBalanceResponseItem, newItem: UserBalanceResponseItem
        ): Boolean = oldItem == newItem
    }

    private companion object {
        const val TAG_RECEIPT = "receita"
    }
}
