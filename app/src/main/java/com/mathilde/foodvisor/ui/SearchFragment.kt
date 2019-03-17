package com.mathilde.foodvisor.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mathilde.foodvisor.R
import com.mathilde.foodvisor.db.model.Combinaison
import com.mathilde.foodvisor.db.model.Food
import com.mathilde.foodvisor.network.api.RetrofitClient
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchFragment.OnFragmentSearchInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchFragment : Fragment() {
    lateinit var combinaisonAdapter: CombinaisonAdapter

    var combinaisons: HashMap<Float, Combinaison> = HashMap()
    var dishs: ArrayList<Food> = ArrayList()
    var starters: ArrayList<Food> = ArrayList()
    var deserts: ArrayList<Food> = ArrayList()
    private var listener: OnFragmentSearchInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        val request = RetrofitClient.getAPIService().getFoodList("bar")
        request.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>?, response: Response<List<Food>>?) {
                response?.body()?.let {
                    for (item in it) {
                        when {
                            item.type == "dish" -> dishs.add(item)
                            item.type == "starter" -> starters.add(item)
                            item.type == "desert" -> deserts.add(item)
                        }
                    }
                    getAllCombinaisons()
                }
            }

            override fun onFailure(call: Call<List<Food>>?, t: Throwable?) {
                Log.d("onFailure", call?.request()?.body().toString())
                Log.d("onFailure", t?.message)
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

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmenSearchtInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentSearchInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentSearchInteractionListener")
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
    interface OnFragmentSearchInteractionListener {
        // TODO: Update argument type and name
        fun onFragmenSearchtInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
