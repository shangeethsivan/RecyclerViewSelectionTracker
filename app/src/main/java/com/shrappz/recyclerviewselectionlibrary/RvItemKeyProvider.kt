package com.shrappz.recyclerviewselectionlibrary

import androidx.recyclerview.selection.ItemKeyProvider

class RvItemKeyProvider(private val adapter: RvAdapter) : ItemKeyProvider<String>(SCOPE_CACHED) {
    override fun getKey(position: Int): String {
        return adapter.currentList[position].id.toString()
    }

    override fun getPosition(key: String): Int {
        return adapter.currentList.indexOfFirst { it.id == key.toInt() }
    }
}