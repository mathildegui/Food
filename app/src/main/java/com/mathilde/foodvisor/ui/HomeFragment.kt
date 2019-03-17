package com.mathilde.foodvisor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mathilde.foodvisor.R
import com.mathilde.foodvisor.db.model.Food
import com.mathilde.foodvisor.viewModel.FoodViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), FoodAdapter.OnFoodClickListener {
    lateinit var foodAdapter: FoodAdapter
    var foods: ArrayList<Food> = ArrayList()
    private var model: FoodViewModel? = null

    override fun onFoodItemClick(position: Int, item: Food) {
        //TODO
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initView()

        model = activity?.run {
            ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        model?.getFoods()?.observe(this, Observer<List<Food>> { foods ->
            this.foods.addAll(foods)
            foodAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {
        foodAdapter = context?.let { FoodAdapter( this.foods, it, this) }!!
    }

    private fun initView() {
        recycler_view_food.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = foodAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
