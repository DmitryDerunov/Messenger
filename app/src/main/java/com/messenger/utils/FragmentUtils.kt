package com.messenger.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.messenger.R
import com.messenger.ui.AppActivity


fun Fragment.mainActivity(): AppActivity{
    return requireActivity() as AppActivity
}


fun Fragment.findTopNavController(): NavController{
    val topLevelHost  = requireActivity().supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

