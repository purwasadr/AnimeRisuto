package com.alurwa.animerisuto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alurwa.animerisuto.databinding.NetworkStateItemBinding

/**
 * Created by Purwa Shadr Al 'urwa on 18/05/2021
 */

class AnimeLoadStateAdapter(
    private val retryCallback: (() -> Unit)? = null
) : LoadStateAdapter<AnimeLoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder =
        ViewHolder(
            NetworkStateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class ViewHolder(
        private val binding: NetworkStateItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {

            with(binding) {
                pb.isVisible = loadState is LoadState.Loading
                parentError.visibility = if (loadState is LoadState.Error)
                    View.VISIBLE else View.INVISIBLE
                txtError.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                txtError.text = (loadState as? LoadState.Error)?.error?.message
                btnRetry.setOnClickListener {
                    retryCallback?.invoke()
                }
            }
        }
    }
}
