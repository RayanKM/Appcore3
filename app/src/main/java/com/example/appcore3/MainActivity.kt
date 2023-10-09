package com.example.appcore3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcore3.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedFilterType: String = ""
    private lateinit var adapter: ItemsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Option1())
        binding.bottomNavigationView.selectedItemId = R.id.option1
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.option1 -> replaceFragment(Option1())
                R.id.option2 -> replaceFragment(Option2())
                else ->{
                }
            }
            true
        }

    }
    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mn,fragment)
        fragmentTransaction.commit()


    }
}