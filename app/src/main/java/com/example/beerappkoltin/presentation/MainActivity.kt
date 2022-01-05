package com.example.beerappkoltin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beerappkoltin.R
import com.example.beerappkoltin.presentation.ui.beerList.BeerListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: BeerListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = BeerListFragment()
        fragment.arguments = intent.extras
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commitNow()
    }
}