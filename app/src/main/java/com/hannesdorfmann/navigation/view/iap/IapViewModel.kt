package com.hannesdorfmann.navigation.view.iap

import androidx.lifecycle.ViewModel


class IapViewModel(
        private var close: (() -> Unit)?
) : ViewModel() {

    fun onBuyingClicked() {
        close!!()
    }

    fun onBackPressed() = close!!()


    override fun onCleared() {
        super.onCleared()
        close = null
    }
}