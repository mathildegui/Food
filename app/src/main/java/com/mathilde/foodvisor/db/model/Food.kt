package com.mathilde.foodvisor.db.model

/**
 * @author mathilde
 * @version 11/03/2019
 *
 * Example : {
 * "carbs": 9.479999542236328,
 * "display_name": "Endives en salade",
 * "fibers": 2,
 * "proteins": 4.119999885559082,
 * "lipids": 1.7999999523162842,
 * "cal": 17,
 * "type": "starter",
 * "imgUrl": "https://api.foodvisor.io/api/2.8.1/fr-US/food/thumbnail/?food_id=4840baadf214d633c9e4d399ebd94a8e"
 * }
 */
data class Food(val carbs: Float, val display_name: String, val fibers: Float, val proteins: Float, val lipids: Float, val cal: Float, val type: String, val imgUrl: String)