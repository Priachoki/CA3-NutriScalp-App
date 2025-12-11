package com.example.nutriscalp.data

import com.example.nutriscalp.R
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MockFoodApi {
    @GET("foods")
    suspend fun fetchFoods(): List<Food>
}


class FoodService private constructor() {

    private val mockApi = object : MockFoodApi {
        override suspend fun fetchFoods(): List<Food> {

            delay(1000)
            return mockFoodList
        }
    }

    private val mockFoodList = listOf(
        Food(1, "Spinach", R.drawable.spinach, "Rich in iron, folate, and hydration-supporting nutrients.", "Improves circulation and moisture balance.", category = "Dryness,Oiliness", nutrients = "Iron, Folate, Vitamin A, Vitamin C, Magnesium", howToEat = "Lightly cook, steam, or blend into smoothies.", bestPairings = "Avocado, Olive Oil, Eggs"),
        Food(2, "Salmon", R.drawable.salmon, "High in Omega-3 fatty acids.", "Deeply hydrates scalp and reduces inflammation.", category = "Dryness,Inflammation", nutrients = "Omega-3 fats, Protein, Vitamin D, Selenium", howToEat = "Best grilled, baked, or steamed.", bestPairings = "Broccoli, Lemon, Sweet Potatoes"),
        Food(3, "Avocado", R.drawable.avocado, "Rich in Vitamin E and healthy fats.", "Regulates sebum, moisturizes scalp, and reduces irritation.", category = "Dryness,Oiliness,Inflammation", nutrients = "Vitamin E, Vitamin B6, Monounsaturated fats, Potassium", howToEat = "Eat fresh, mash on toast, or add to salads.", bestPairings = "Eggs, Spinach, Tomatoes"),
        Food(4, "Sweet Potatoes", R.drawable.sweet_potato, "Packed with Beta-Carotene.", "Boosts natural oil (sebum) production for dry scalp.", category = "Dryness", nutrients = "Beta-carotene, Vitamin A, Fiber, Potassium", howToEat = "Roast, mash, or air-fry for best nutrient retention.", bestPairings = "Salmon, Olive Oil"),
        Food(5, "Almonds", R.drawable.almond, "High in Vitamin E and healthy fats.", "Strengthens scalp barrier and reduces dryness.", category = "Dryness", nutrients = "Vitamin E, Healthy fats, Zinc, Biotin", howToEat = "Eat raw or roasted; add to oatmeal or smoothies.", bestPairings = "Oats, Greek Yogurt, Berries"),
        Food(6, "Olive Oil", R.drawable.olive_oil, "Contains antioxidants and oleic acid.", "Restores moisture and reduces scalp inflammation.", category = "Dryness,Inflammation", nutrients = "Monounsaturated fats, Polyphenols, Vitamin E", howToEat = "Use as a salad dressing or drizzle over warm foods.", bestPairings = "Spinach, Tomatoes, Sweet Potatoes"),
        Food(7, "Eggs", R.drawable.egg, "Excellent source of protein and biotin.", "Improves scalp hydration while strengthening follicles.", category = "Dryness", nutrients = "Protein, Biotin, Vitamin D, Vitamin B12", howToEat = "Boiled, poached, or scrambled with minimal oil.", bestPairings = "Avocado, Spinach, Whole Grain Toast"),
        Food(8, "Chia Seeds", R.drawable.chia_seed, "Rich in Omega-3 and antioxidants.", "Enhances moisture retention and reduces redness.", category = "Dryness,Inflammation", nutrients = "Omega-3 fats, Fiber, Antioxidants, Calcium", howToEat = "Add to yogurt bowls, smoothies, or chia pudding.", bestPairings = "Greek Yogurt, Berries, Almonds"),
        Food(9, "Greek Yogurt", R.drawable.greek_yougurt, "High in probiotics and protein.", "Balances scalp microbiome and reduces flakiness.", category = "Dryness,Inflammation", nutrients = "Protein, Probiotics, Calcium, Vitamin B12", howToEat = "Eat plain, or mix with fruits and nuts.", bestPairings = "Chia Seeds, Almonds, Berries"),
        Food(10, "Coconut", R.drawable.coconut, "Contains medium-chain fatty acids.", "Deep hydration for excessively dry scalp skin.", category = "Dryness", nutrients = "MCT fats, Iron, Vitamin C", howToEat = "Use raw, grated, or as coconut milk.", bestPairings = "Oats, Pineapple, Mango"),
        Food(11, "Walnuts", R.drawable.walnuts, "A powerhouse of Omega-3 & Omega-6 fats.", "Balances scalp oils, reduces dryness, and calms inflammation.", category = "Dryness,Oiliness,Inflammation", nutrients = "Omega-3 & Omega-6 fats, Copper, Protein", howToEat = "Eat raw or add to salads and yogurt.", bestPairings = "Spinach, Oats, Apples"),
        Food(12, "Pumpkin", R.drawable.pumpkin, "High in Vitamin A and zinc.", "Boosts sebum production naturally in dry scalps.", category = "Dryness", nutrients = "Vitamin A, Zinc, Fiber, Potassium", howToEat = "Roast or blend into soups.", bestPairings = "Walnuts, Olive Oil"),
        Food(13, "Kiwi", R.drawable.kiwi, "Rich in Vitamin C and antioxidants.", "Boosts collagen for scalp hydration and elasticity.", category = "Dryness", nutrients = "Vitamin C, Vitamin K, Potassium, Folate", howToEat = "Eat fresh or add to smoothie bowls.", bestPairings = "Greek Yogurt, Berries"),
        Food(14, "Broccoli", R.drawable.broccoli, "Loaded with Vitamins C & K.", "Hydrates scalp and protects against oxidative stress.", category = "Dryness", nutrients = "Vitamin C, Vitamin K, Fiber, Folate", howToEat = "Steam lightly or roast.", bestPairings = "Salmon, Lemon"),
        Food(15, "Cucumber", R.drawable.cucumber, "Extremely hydrating vegetable.", "Soothes dryness and irritation instantly.", category = "Dryness,Inflammation", nutrients = "Water, Vitamin K, Potassium", howToEat = "Eat raw or blend into juices.", bestPairings = "Mint, Lemon"),
        Food(16, "Blueberries", R.drawable.blueberries, "Very high in antioxidants.", "Repairs inflammation and oxidative scalp damage.", category = "Inflammation", nutrients = "Antioxidants, Vitamin C, Fiber", howToEat = "Eat fresh or add to yogurt/oats.", bestPairings = "Greek Yogurt, Chia Seeds"),
        Food(17, "Green Tea", R.drawable.greentea, "Anti-fungal and anti-inflammatory properties.", "Controls scalp oiliness and reduces irritation.", category = "Oiliness,Inflammation", nutrients = "Catechins, Antioxidants, L-theanine", howToEat = "Brew fresh; avoid adding sugar.", bestPairings = "Lemon, Honey"),
        Food(18, "Flax Seeds", R.drawable.flax_seeds, "Rich in Omega-3 fatty acids.", "Great for inflammation and maintaining scalp moisture.", category = "Dryness,Inflammation", nutrients = "Omega-3 fats, Fiber, Protein", howToEat = "Add to smoothies or oatmeal.", bestPairings = "Oats, Greek Yogurt"),
        Food(19, "Garlic", R.drawable.garlic, "Contains allicin, a strong anti-inflammatory compound.", "Reduces scalp irritation and improves circulation.", category = "Inflammation", nutrients = "Allicin, Manganese, Vitamin B6", howToEat = "Cook lightly or roast for better digestion.", bestPairings = "Olive Oil, Tomatoes"),
        Food(20, "Ginger", R.drawable.ginger, "Natural warming anti-inflammatory spice.", "Improves blood flow and reduces irritation.", category = "Inflammation", nutrients = "Gingerol, Vitamin B6, Magnesium", howToEat = "Use in teas, soups, or stir-fries.", bestPairings = "Lemon, Honey"),
        Food(21, "Tomatoes", R.drawable.tomato, "Rich in lycopene and antioxidants.", "Helps balance scalp oil and prevents clogged follicles.", category = "Oiliness", nutrients = "Lycopene, Vitamin C, Potassium", howToEat = "Eat raw, roasted, or as fresh salsa.", bestPairings = "Avocado, Olive Oil"),
        Food(22, "Oats", R.drawable.oats, "Naturally soothing and hydrating.", "Reduces itching and dryness from irritation.", category = "Dryness,Inflammation", nutrients = "Fiber, Zinc, Iron, Magnesium", howToEat = "Make warm oatmeal or add to smoothies.", bestPairings = "Almonds, Blueberries"),
        Food(23, "Lemon", R.drawable.lemons, "High in Vitamin C and acidic pH.", "Controls grease and exfoliates oily scalp.", category = "Oiliness", nutrients = "Vitamin C, Citric Acid", howToEat = "Add to water, salads, or freshly squeezed juice.", bestPairings = "Green Tea, Salmon"),
        Food(24, "Turmeric", R.drawable.tumeric, "Strong anti-inflammatory spice.", "Helps calm redness and itching on the scalp.", category = "Inflammation", nutrients = "Curcumin, Iron, Manganese", howToEat = "Add to curries, golden milk, or soups.", bestPairings = "Ginger, Black Pepper")
    )



    suspend fun getFoods(): List<Food> {
        return mockApi.fetchFoods()
    }

    companion object {
        val instance by lazy { FoodService() }
    }
}