package com.example.job_2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.job_2.data.UserProfile
import com.example.job_2.databinding.ItemProfileBinding

class ProfileAdapter(private val clickListener: OnItemClickListener) :
    ListAdapter<UserProfile, ProfileAdapter.ProfileViewHolder>(ProfileDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(profile: UserProfile)
        fun onEditClick(profile: UserProfile)
        fun onDeleteClick(profile: UserProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileBinding.inflate(layoutInflater, parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = getItem(position)
        holder.bind(profile, clickListener)
    }

    class ProfileViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: UserProfile, listener: OnItemClickListener) {
            binding.profile = profile
            binding.clickListener = listener
            binding.executePendingBindings()
        }
    }

    class ProfileDiffCallback : DiffUtil.ItemCallback<UserProfile>() {
        override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
            return oldItem == newItem
        }
    }
}