package com.example.utspam

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.utspam.databinding.ActivityHomeBinding
import com.example.utspam.fragment.BerandaFragment
import com.example.utspam.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    val FragmentBeranda : Fragment = BerandaFragment()
    val FragmentProfile : Fragment = ProfileFragment()

    val fm : FragmentManager = supportFragmentManager
    var active : Fragment = FragmentBeranda

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var menu : Menu
    private lateinit var menuItem : MenuItem

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonNavigation()
    }

    private fun buttonNavigation() {
        fm.beginTransaction().add(R.id.container , FragmentBeranda).show(FragmentBeranda).commit()
        fm.beginTransaction().add(R.id.container , FragmentProfile).hide(FragmentProfile).commit()

        bottomNavigationView = binding.navView
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    callFragment(0 , FragmentBeranda)
                }
                R.id.nav_profile -> {
                    callFragment(1 , FragmentProfile)
                }
            }
            true
        }
    }

    private fun callFragment(index : Int , fragment: Fragment) {
        menuItem = menu.getItem(index)
        menuItem.isChecked = true
        val transaction = fm.beginTransaction()
        transaction.hide(active)
        transaction.show(fragment)
        transaction.commit()

        active = fragment
    }

}