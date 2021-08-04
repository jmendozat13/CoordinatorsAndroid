package com.hannesdorfmann.navigation.view.onboarding.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hannesdorfmann.navigation.domain.user.AuthenticatedUser
import com.hannesdorfmann.navigation.domain.user.Usermanager
import io.reactivex.disposables.Disposable

class WelcomeViewModel(
        private val usermanager: Usermanager,
        private var onNextClicked: (() -> Unit)?
) : ViewModel() {

    private val disposable: Disposable
    val username = MutableLiveData<String>()

    init {
        disposable = usermanager.currentUser
                .filter { it is AuthenticatedUser }
                .subscribe { username.value = (it as AuthenticatedUser).username }
    }


    fun onNextClicked() {
        onNextClicked!!()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        onNextClicked = null
    }
}