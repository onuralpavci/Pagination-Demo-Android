package com.example.paginationdemo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationdemo.databinding.ItemPassengerRowBinding
import com.example.paginationdemo.utils.PersonResponse

class VerticalPassengerViewHolder(
    private val binding: ItemPassengerRowBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(passengerResponse: PersonResponse) {
        val passengerName = passengerResponse.fullName
        val passengerTrip = passengerResponse.id.toString()

        with(binding) {
            passengerNameTextView.text = passengerName
            passengerTripsTextView.text = passengerTrip
        }
    }

    companion object {
        fun create(parent: ViewGroup): VerticalPassengerViewHolder {
            return VerticalPassengerViewHolder(
                ItemPassengerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
