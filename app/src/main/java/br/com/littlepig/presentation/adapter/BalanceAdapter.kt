package br.com.littlepig.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

        return BalanceAdapterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BalanceAdapterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BalanceAdapterViewHolder(
        private val binding: CardViewBalanceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(balance: UserBalanceResponseItem) = with(binding) {
            valueBalance.text = balance.saldo.formatCurrency()
        }
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
