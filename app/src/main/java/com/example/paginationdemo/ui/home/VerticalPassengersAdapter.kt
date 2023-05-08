package com.example.paginationdemo.ui.home

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.paginationdemo.utils.PersonResponse

class VerticalPassengersAdapter :
    PagingDataAdapter<PersonResponse, VerticalPassengerViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalPassengerViewHolder {
        return VerticalPassengerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: VerticalPassengerViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
