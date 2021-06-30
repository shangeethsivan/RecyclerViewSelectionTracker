package com.shrappz.recyclerviewselectionlibrary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrappz.recyclerviewselectionlibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RvAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter


        val list = listOf(RvItem(1), RvItem(2), RvItem(3), RvItem(4))
        adapter.submitList(list)

        val tracker = SelectionTracker.Builder(
            "mySelection",
            binding.recyclerView,
            RvItemKeyProvider(adapter),
            RvItemDetailsLookup(binding.recyclerView),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapter.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<String>() {
            @SuppressLint("SetTextI18n")
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                val items = tracker.selection.size()
                binding.dummy.text = "Selected items $items"
            }
        })
    }
}