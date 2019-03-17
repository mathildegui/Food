package com.mathilde.foodvisor.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mathilde.foodvisor.R
import com.mathilde.foodvisor.db.model.Food
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_food.view.*

/**
 * @author mathilde
 * @version 12/03/2019
 */
class FoodAdapter(private val foods: List<Food>, private val context: Context, private val listener: OnFoodClickListener? = null)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    interface OnFoodClickListener {
        fun onFoodItemClick(position: Int, item: Food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_food, parent, false))

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {
        val food = foods[position]

        holder.foodCal.text  = food.cal.toString()
        holder.foodName.text = food.display_name
        holder.foodType.text = food.type
        holder.foodCarbs.text = food.carbs.toString()
        holder.foodFiber.text = food.fibers.toString()
        holder.foodProteins.text = food.proteins.toString()
        holder.foodLipids.text = food.lipids.toString()
        Picasso.get().load(food.imgUrl).into(holder.foodImg)

        holder.itemView.setOnClickListener {
            listener?.onFoodItemClick(position, food)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val foodCal: TextView  = v.food_item_cal
        val foodName: TextView = v.food_item_display_name
        val foodType: TextView = v.food_item_type
        val foodCarbs: TextView = v.food_item_carbs
        val foodFiber: TextView = v.food_item_fiber
        val foodProteins: TextView = v.food_item_proteins
        val foodLipids: TextView = v.food_item_lipids
        val foodImg: ImageView = v.food_item_img
    }
}