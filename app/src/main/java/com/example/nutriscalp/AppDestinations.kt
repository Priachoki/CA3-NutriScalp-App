package com.example.nutriscalp

object AppDestinations {
    const val SPLASH_ROUTE = "splash"
    const val LOGIN_ROUTE = "login"
    const val HOME_ROUTE = "home"
    const val FOODS_ROUTE = "foods"
    const val TIPS_ROUTE = "tips"
    const val SETTINGS_ROUTE = "settings"
    const val DIET_LOG_ROUTE = "diet_log"

    // 💡 NEW ROUTES
    const val MEAL_HISTORY_ROUTE = "meal_history"
    const val FOOD_DETAIL_ROUTE = "food_detail/{foodId}"
    const val FOOD_DETAIL_BASE_ROUTE = "food_detail"

    // 💡 NEW: Separate splash screen for post-login
    const val POST_LOGIN_SPLASH_ROUTE = "post_login_splash"
}