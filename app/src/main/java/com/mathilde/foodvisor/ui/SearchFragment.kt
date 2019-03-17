package com.mathilde.foodvisor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mathilde.foodvisor.R
import com.mathilde.foodvisor.db.model.Combinaison
import com.mathilde.foodvisor.db.model.Food
import com.mathilde.foodvisor.viewModel.FoodViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.set
import kotlin.collections.toSortedMap

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    lateinit var combinaisonAdapter: CombinaisonAdapter
    private var model: FoodViewModel? = null

    var combinaisons: HashMap<Float, Combinaison> = HashMap()
    var starters: ArrayList<Food> = ArrayList()
    var dishs: ArrayList<Food> = ArrayList()
    var deserts: ArrayList<Food> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        model = activity?.run {
            ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        model?.getFoods()?.observe(this, Observer<List<Food>> { foods ->
            foods.let {
                for (item in it) {
                    when {
                        item.type == "dish" -> dishs.add(item)
                        item.type == "starter" -> starters.add(item)
                        item.type == "desert" -> deserts.add(item)
                    }
                }
                getAllCombinaisons()
            }
        })

        search_bt.setOnClickListener{
            if(search_et.text.toString().isNotEmpty()) {
                combinaisonAdapter.updateCals(search_et.text.toString().toFloat())
                recycler_view_food.visibility = VISIBLE
            }
        }
    }

    private fun initAdapter() {
        combinaisonAdapter = context?.let { CombinaisonAdapter( this.combinaisons, 45f, it) }!!
        recycler_view_food.apply {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = combinaisonAdapter
        }
        recycler_view_food.visibility = INVISIBLE
    }

    fun getAllCombinaisons () {
        for (starter in this.starters) {
            for (dish in this.dishs) {
                for (desert in this.deserts) {
                    val total = starter.cal + dish.cal + desert.cal
                    this.combinaisons[total] = Combinaison(starter, dish, desert)
                }
            }
        }

        this.combinaisons.toSortedMap()
        combinaisonAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
