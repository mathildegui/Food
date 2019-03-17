package com.mathilde.foodvisor.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mathilde.foodvisor.R
import com.mathilde.foodvisor.db.model.Combinaison
import kotlinx.android.synthetic.main.item_combinaison.view.*

/**
 * @author mathilde
 * @version 15/03/2019
 */
class CombinaisonAdapter (private var combinaisons:  HashMap<Float, Combinaison>, private var cals: Float, private val context: Context) : RecyclerView.Adapter<CombinaisonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CombinaisonAdapter.ViewHolder {
        return CombinaisonAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_combinaison, parent, false))
    }

    override fun getItemCount(): Int = combinaisons.size

    fun updateCals(cals: Float) {
        this.cals = cals
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CombinaisonAdapter.ViewHolder, position: Int) {

        val allCombinaisons = combinaisons.values.toList()
        val combination = allCombinaisons[position]

        val totalDiff = combination.starter.cal + combination.dish.cal + combination.desert.cal

        var percentage = (cals - totalDiff) / cals
        if(percentage < 0) {
            percentage *= -1
        }

        when {
            percentage <= 0.1 -> {
                holder.foodCalDiff.text = totalDiff.toString().plus(" calories au total, classement A")
                holder.foodCalDiff.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            percentage in 0.1..0.2  -> {
                holder.foodCalDiff.text = totalDiff.toString().plus(" calories au total, classement B")
                holder.foodCalDiff.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            }
            percentage in 0.2..0.3 -> {
                holder.foodCalDiff.text = totalDiff.toString().plus(" calories au total, classement C")
                holder.foodCalDiff.setTextColor(ContextCompat.getColor(context, R.color.orange))
            }
            percentage > 0.3-> {
                holder.foodCalDiff.text = totalDiff.toString().plus(" calories au total, classement D")
                holder.foodCalDiff.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
        }

        holder.starter.text = "Entrée: ".plus(combination.starter.display_name).plus(": ")
        holder.dish.text = "Plat: ".plus(combination.dish.display_name)
        holder.desert.text = "Dessert: ".plus(combination.desert.display_name)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val foodCalDiff: TextView = v.combinaison_item_total_diff
        val starter: TextView = v.combinaison_item_starter
        val dish: TextView = v.combinaison_item_dish
        val desert: TextView = v.combinaison_item_desert
    }
}