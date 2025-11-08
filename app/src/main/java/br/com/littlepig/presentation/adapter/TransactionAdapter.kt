package br.com.littlepig.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.littlepig.R
import br.com.littlepig.data.model.balance.Balance
import br.com.littlepig.databinding.CardViewTransactionBinding
import br.com.littlepig.utils.formatCurrency

class TransactionAdapter(
    val onClick: (String) -> Unit
) : ListAdapter<Balance, TransactionAdapter.TransactionAdapterViewHolder>(
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

    inner class TransactionAdapterViewHolder(
        private val binding: CardViewTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(balance: Balance) = with(binding) {
            typeTransaction.apply {
                when (balance.type == TAG_RECEIPT) {
                    true -> {
                        setChipBackgroundColorResource(R.color.green)
                        setChipIconResource(R.drawable.ic_arrow_up)
                    }

                    false -> {
                        setChipBackgroundColorResource(R.color.red)
                        setChipIconResource(R.drawable.ic_arrow_down)
                    }
                }
                text = balance.type
            }
            valueTransaction.text = balance.value.formatCurrency()
            deleteTransaction.setOnClickListener {
                onClick(balance.id)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Balance>() {
        override fun areItemsTheSame(
            oldItem: Balance, newItem: Balance
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Balance, newItem: Balance
        ): Boolean = oldItem == newItem
    }

    private companion object {
        const val TAG_RECEIPT = "receita"
    }
}
