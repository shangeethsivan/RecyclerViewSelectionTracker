package com.shrappz.recyclerviewselectionlibrary

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shrappz.recyclerviewselectionlibrary.databinding.ItemSelectionItemBinding

class RvAdapter : ListAdapter<RvItem, RvAdapter.RvViewHolder>(DiffUtilCallback()) {

    var tracker: SelectionTracker<String>? = null

    class DiffUtilCallback : DiffUtil.ItemCallback<RvItem>() {
        override fun areItemsTheSame(oldItem: RvItem, newItem: RvItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RvItem, newItem: RvItem): Boolean {
            return oldItem == newItem
        }
    }

    init {
        setHasStableIds(true)
    }

    inner class RvViewHolder(private val itemViewBinding: ItemSelectionItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(item: RvItem, isSelected: Boolean) = with(itemViewBinding) {
            if (isSelected) {
                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.teal_700
                    )
                )
            } else {
                container.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
            }
            itemTextView.text = item.id.toString()
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int {
                    return adapterPosition
                }

                override fun getSelectionKey(): String {
                    return getItem(adapterPosition).id.toString()
                }

                override fun inSelectionHotspot(e: MotionEvent): Boolean {
                    return super.inSelectionHotspot(e)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val context = LayoutInflater.from(parent.context)
        return RvViewHolder(ItemSelectionItemBinding.inflate(context, parent, false))
    }

    override fun onBindViewHolder(holderRv: RvViewHolder, position: Int) {
        val item = getItem(position)
        tracker?.let {
            holderRv.bind(item, it.isSelected(item.id.toString()))
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

}

data class RvItem(val id: Int)