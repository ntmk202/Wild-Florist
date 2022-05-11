package com.ntmk.myapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ntmk.myapp.R
import com.ntmk.myapp.adapters.OnBoardingAdapter
import com.ntmk.myapp.databinding.ActivityAdvertisementBinding
import com.ntmk.myapp.model.OnBoardingData

class AdvertisementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdvertisementBinding
//    add init
    private var onBoardingAdapter: OnBoardingAdapter? = null
    private var onBoardingViewPaper: ViewPager? = null
    var position = 0
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_advertisement)


        if(restorePrefData()){
            gotoLogin()
            finish()
        }

        // add data list
        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Flower","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            R.drawable.sl1
        ))
        onBoardingData.add(OnBoardingData("Payment","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            R.drawable.sl2
        ))
        onBoardingData.add(OnBoardingData("Delivery","Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            R.drawable.sl3
        ))

        setViewPaperAdapter(onBoardingData)

        // add onClickListener for btn_bottom
        position = onBoardingViewPaper!!.currentItem
        binding.btnNext.setOnClickListener{

            if(position<onBoardingData.size){
                position++
                onBoardingViewPaper!!.currentItem = position
            }
            if(position == onBoardingData.size){
                savePreData()
                gotoLogin()
            }
        }
        binding.btnNext.setOnClickListener{
            gotoLogin()
        }

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                position = tab!!.position
                if(tab.position == onBoardingData.size - 1){
                    "FINISH".also { binding.btnNext.text = it }
                }else{
                    "NEXT".also { binding.btnNext.text = it }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setViewPaperAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPaper = binding.SlideVIewPaper
        onBoardingAdapter = OnBoardingAdapter(this, onBoardingData)
        onBoardingViewPaper?.adapter = onBoardingAdapter
        binding.tab.setupWithViewPager(onBoardingViewPaper)
    }

    private fun gotoLogin(){
        val i= Intent(applicationContext, LoginActivity::class.java)
        startActivity(i)
    }

    private fun savePreData(){
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putBoolean("isFirstTimeRun",true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean{
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTimeRun", false)
    }
}