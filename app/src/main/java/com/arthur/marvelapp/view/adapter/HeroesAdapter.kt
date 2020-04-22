package com.arthur.marvelapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arthur.marvelapp.R
import com.arthur.marvelapp.model.Character
import com.arthur.marvelapp.util.load
import kotlinx.android.synthetic.main.item_heroes.view.*


class HeroesAdapter : PagedListAdapter<Character, HeroesAdapter.VH>(
    characterDiff
) {

    //Item clicado / click
    private var mItemClickListener: ItemClickListener? = null

    fun addItemClickListener(listener: ItemClickListener) {
        mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_heroes, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val character = getItem(position)
        holder.txtName.text = character?.name
        holder.imgThumbnail.load(
            "${character?.thumbnail?.path}/standard_medium.${character?.thumbnail?.extension}",
            holder.imgThumbnail
        )
        holder.imgThumbnail.setOnClickListener(View.OnClickListener {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(character)
            }
        })
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(old: Character, new: Character): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Character, new: Character): Boolean {
                return old == new
            }

        }
    }

    //Define your Interface method here
    interface ItemClickListener {
        fun onItemClick(event: Character?)
    }
}