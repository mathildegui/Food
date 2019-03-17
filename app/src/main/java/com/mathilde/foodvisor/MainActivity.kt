package com.mathilde.foodvisor

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mathilde.foodvisor.db.model.Food
import com.mathilde.foodvisor.ui.HomeFragment
import com.mathilde.foodvisor.ui.SearchFragment
import com.mathilde.foodvisor.viewModel.FoodViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragment.OnFragmenHometInteractionListener,
    SearchFragment.OnFragmentSearchInteractionListener {
    override fun onFragmenSearchtInteraction(uri: Uri) {

    }

    override fun onFragmentInteraction(uri: Uri) {

    }

//    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                addFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                addFragment(SearchFragment.newInstance("yo", "yu"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        addFragment(HomeFragment.newInstance())


        val model = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        model.getFoods().observe(this, Observer<List<Food>>{ foods ->
            Log.d("ViewModelProviders", foods.toString())
        })
    }

    /**
     * add/replace fragment in container [FrameLayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
