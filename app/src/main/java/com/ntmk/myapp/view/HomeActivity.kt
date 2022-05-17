package com.ntmk.myapp.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityHomeBinding
import com.ntmk.myapp.view.ui.profile.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)



//        binding = ActivityHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_garden, R.id.navigation_notification,R.id.navigation_profile
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null

//        replaceFragment(ProfileFragment.newInstance(),false)
    }

//    fun replaceFragment(fragment: Fragment, istransition:Boolean){
//        val fragmentTransition = supportFragmentManager.beginTransaction()
//
//        if (istransition){
//            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
//        }
//        fragmentTransition.add(R.id.nav_host_fragment_activity_home,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        val fragments = supportFragmentManager.fragments
//        if (fragments.size == 0){
//            finish()
//        }
//    }
}