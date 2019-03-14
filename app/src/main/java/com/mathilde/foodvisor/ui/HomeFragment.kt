package com.mathilde.foodvisor.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mathilde.foodvisor.R
import com.mathilde.foodvisor.network.api.RetrofitClient
import com.mathilde.foodvisor.db.model.Food
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmenHometInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment(), FoodAdapter.OnFoodClickListener {
    lateinit var foodAdapter: FoodAdapter
    var foods: ArrayList<Food> = ArrayList()


    override fun onFoodItemClick(position: Int, item: Food) {
        //TODO
    }

    private var listener: OnFragmenHometInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = RetrofitClient.getAPIService().getFoodList("bar")

        initAdapter()
        initView()

        request.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>?, response: Response<List<Food>>?) {
                response?.body()?.let { foods.addAll(it)
                Log.d("FOODS", foods.toString())
                }
                foodAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Food>>?, t: Throwable?) {
                Log.d("onFailure", call?.request()?.body().toString())
                Log.d("onFailure", t?.message)
            }
        })
    }

    private fun initAdapter() {
        foodAdapter = context?.let { FoodAdapter( this.foods, it, this) }!!
    }

    private fun initView() {

        recycler_view_food.apply {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = foodAdapter
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmenHometInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmenHometInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmenHometInteractionListener {
        fun onFragmentInteraction(uri: Uri)
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
