package com.hannesdorfmann.navigation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hannesdorfmann.navigation.domain.user.LoginStateMachine
import com.hannesdorfmann.navigation.utils.plus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
        private val loginStateMachine: LoginStateMachine,
        private var onSignUpClicked: (() -> Unit)?,
        private var onForgotPasswordClicked: (() -> Unit)?
) : ViewModel() {

    private val mState: MutableLiveData<LoginViewState> = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState> get() = mState

    private val disposables = CompositeDisposable()

    init {
        val sharedLoginState = loginStateMachine.state
                .subscribeOn(Schedulers.io())
                .share()
                .observeOn(AndroidSchedulers.mainThread())

        disposables + sharedLoginState
                .filter { it != LoginStateMachine.State.Successful }
                .map {
                    when (it) {
                        LoginStateMachine.State.Loading -> LoginViewState.LoadingState
                        LoginStateMachine.State.UnknownUserError -> LoginViewState.ShowLoginFormWithErrorState
                        else -> throw IllegalStateException("$it should never be reached")
                    }
                }
                .startWith(LoginViewState.ShowLoginForm)
                .subscribe {
                    mState.value = it
                }
    }


    fun login(loginCredentials: LoginStateMachine.LoginCredentials) {
        loginStateMachine.input.accept(loginCredentials)
    }

    fun signUp(){
        onSignUpClicked!!()
    }

    fun forgotPassword(){
        onForgotPasswordClicked!!()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        onSignUpClicked = null
        onForgotPasswordClicked = null
    }
}


sealed class LoginViewState {
    object ShowLoginForm : LoginViewState()
    object LoadingState : LoginViewState()
    object ShowLoginFormWithErrorState : LoginViewState()
}