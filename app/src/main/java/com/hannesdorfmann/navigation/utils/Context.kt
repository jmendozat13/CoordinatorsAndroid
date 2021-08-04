package com.hannesdorfmann.navigation.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hannesdorfmann.navigation.MyApp
import com.hannesdorfmann.navigation.coordinator.Navigator
import com.hannesdorfmann.navigation.coordinator.RootFlowCoordinator
import com.hannesdorfmann.navigation.view.iap.IapViewModel

val Fragment.application
    get() = requireActivity().application as MyApp

val Fragment.viewModelFactory: ViewModelProvider.Factory
    get() = application.viewModelFactory


val Activity.navigator: Navigator
    get() = (application as MyApp).viewModelFactory.navigator

val Activity.rootFlowCoordinator: RootFlowCoordinator
    get() = (application as MyApp).viewModelFactory.rootCoordinator



inline fun <reified VM : ViewModel> Fragment.getViewModel(): VM {
    return ViewModelProvider(this, application.viewModelFactory)[VM::class.java]
}