package com.example.cachedemo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cachedemo.R
import com.example.cachedemo.model.CharResult
import kotlinx.android.synthetic.main.item_characters_adapter.view.*

open class CharacterAdapter(val context: Context, var arrayList: ArrayList<CharResult>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_characters_adapter, parent, false)

        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val customViewHolder = holder as CustomViewHolder
        if (arrayList.size > 0) {
            val obj = arrayList[holder.adapterPosition]
            customViewHolder.bindData(obj, position)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    open inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var positions: Int = 0

        init {
            itemView.setOnClickListener {
                onClick(positions)
            }
        }

        open fun bindData(data: CharResult, position: Int) {
            positions = position
            Glide.with(context)
                .load(data.image)
                .into(itemView.imgCharacter)

            itemView.tvCharacterName.text = data.name.toString()
        }

    }

    open fun onClick(position: Int) {

    }
}