package com.example.myapplication.presentation.navigation

object Routes {
    const val APP_LIST = "app_list"
    const val APP_DETAILS = "app_details/{appId}"

    fun appDetails(appId: String) = "app_details/$appId"
}
