package com.meaningstest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.meaningstest.databinding.MeaningsItemBinding
import com.meaningstest.model.MeaningsResponse
import javax.inject.Inject

class UsersListAdapter @Inject constructor() : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {


    private val diffUtil = object : DiffUtil.ItemCallback<MeaningsResponse.Lf>() {
        override fun areItemsTheSame(oldItem: MeaningsResponse.Lf, newItem: MeaningsResponse.Lf): Boolean {
            return oldItem.lf == newItem.lf
        }

        override fun areContentsTheSame(
            oldItem: MeaningsResponse.Lf,
            newItem: MeaningsResponse.Lf
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    inner class ViewHolder(val binding: MeaningsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MeaningsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meanings = differ.currentList[position]
        holder.binding.apply {
            tvMeaning.text = meanings.lf
            tvFreq.text = "Freq: "+meanings.freq
            tvSince.text = "Since: "+meanings.since
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}