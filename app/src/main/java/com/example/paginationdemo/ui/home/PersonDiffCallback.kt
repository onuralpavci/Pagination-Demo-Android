package com.example.paginationdemo.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.example.paginationdemo.utils.PersonResponse

class PersonDiffCallback : DiffUtil.ItemCallback<PersonResponse>() {
    override fun areItemsTheSame(oldItem: PersonResponse, newItem: PersonResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonResponse, newItem: PersonResponse): Boolean {
        return oldItem == newItem
    }
}
