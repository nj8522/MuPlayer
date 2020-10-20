package com.ncode.muplayer.navigationhost

import androidx.fragment.app.Fragment

interface NavigationHost {

    fun navigateTo(fragement : Fragment, addToBackStack : Boolean)

}