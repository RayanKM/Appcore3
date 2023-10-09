package com.example.appcore3

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcore3.databinding.FragmentOption1Binding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Option1 : Fragment(R.layout.fragment_option1) {
    private var selectedFilterType: String = ""
    private lateinit var adapter: ItemsAdapter
    private lateinit var binding: FragmentOption1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOption1Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val booksDataList = readBooksDataFromCSV(requireContext())
        adapter = ItemsAdapter(requireContext(), booksDataList)

        binding.optionsButton.setOnClickListener { view ->
            showOptionsMenu(view)
        }
        // Initialize your RecyclerView and adapter
        val recyclerView = binding.mainRecyclerview
        adapter = ItemsAdapter(requireContext(), booksDataList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Set an item click listener
        adapter.setOnItemClickListener(object : ItemsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("qsddqdsq", booksDataList[position].toString())
            }
        })

    }

    fun readBooksDataFromCSV(context: Context): List<BooksDataModel> {
        val booksDataList = mutableListOf<BooksDataModel>()
        val inputStream: InputStream = context.resources.openRawResource(R.raw.groups)

        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            var line: String?
            var isFirstLine = true // Add this flag

            while (reader.readLine().also { line = it } != null) {
                if (isFirstLine) {
                    isFirstLine = false
                    continue // Skip the first line (header)
                }

                val tokens = line?.split(",")
                if (tokens != null && tokens.size >= 5) {
                    val title = tokens[1].trim()
                    val location = tokens[2].trim()
                    val type = tokens[3].trim()
                    val time = tokens[4].trim()
                    booksDataList.add(BooksDataModel(title, location, time))
                }
            }
        }
        return booksDataList
    }
    private fun showOptionsMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.filter, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.Uns -> {
                    selectedFilterType = "UN Students"
                    filterRecyclerView()
                    true
                }
                R.id.Xsports -> {
                    selectedFilterType = "Xsports"
                    filterRecyclerView()
                    true
                }
                R.id.Apolitical -> {
                    selectedFilterType = "Apolitical"
                    filterRecyclerView()
                    true
                }
                R.id.Football -> {
                    selectedFilterType = "Football"
                    filterRecyclerView()
                    true
                }
                R.id.All -> {
                    selectedFilterType = ""
                    filterRecyclerView()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
    private fun filterRecyclerView() {
        val filteredList = if (selectedFilterType.isEmpty()) {
            readBooksDataFromCSV(requireContext()) // Show all items if no filter is selected
        } else {
            readBooksDataFromCSV(requireContext()).filter { it.title == selectedFilterType }
        }


        Log.d("qsddqdsq", "$filteredList")

        // Update the data in the adapter
        adapter.setItems(filteredList)

        // Refresh the RecyclerView
        adapter.notifyDataSetChanged()
    }
}