package com.mathilde.foodvisor.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mathilde.foodvisor.db.model.Food
import com.mathilde.foodvisor.network.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author mathilde
 * @version 17/03/2019
 */
class FoodViewModel : ViewModel() {
    private val foods: MutableLiveData<List<Food>> by lazy {
        MutableLiveData<List<Food>>().also {
            loadFoods()
        }
    }

    fun getFoods(): LiveData<List<Food>> {
        return foods
    }

    private fun loadFoods() {
        val request = RetrofitClient.getAPIService().getFoodList("bar")
        request.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>?, response: Response<List<Food>>?) {
                response?.body()?.let {
                    foods.postValue(it)
                }
            }

            override fun onFailure(call: Call<List<Food>>?, t: Throwable?) {
                Log.d("onFailure", call?.request()?.body().toString())
                Log.d("onFailure", t?.message)
            }
        })
    }
}