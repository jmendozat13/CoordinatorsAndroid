package com.hannesdorfmann.navigation.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.hannesdorfmann.navigation.databinding.FragmentLoginBinding
import com.hannesdorfmann.navigation.domain.user.LoginStateMachine
import com.hannesdorfmann.navigation.utils.getViewModel
import com.hannesdorfmann.navigation.utils.gone
import com.hannesdorfmann.navigation.utils.visible

class LoginFragment : Fragment() {

    private lateinit var bindingFragmentLogin: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFragmentLogin = FragmentLoginBinding.inflate(inflater, container, false)
        return bindingFragmentLogin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: LoginViewModel = getViewModel()
        with(bindingFragmentLogin) {
            login.setOnClickListener {
                viewModel.login(
                    LoginStateMachine.LoginCredentials(
                        username = username.text.toString(),
                        password = password.text.toString()
                    )
                )
            }
            register.setOnClickListener {
                viewModel.signUp()
            }
            forgotPassword.setOnClickListener {
                viewModel.forgotPassword()
            }
        }
        viewModel.state.observe(viewLifecycleOwner, this::render)
    }

    private fun render(state: LoginViewState) = with(bindingFragmentLogin){
        TransitionManager.beginDelayedTransition(loginContainer)
        when (state) {
            LoginViewState.LoadingState -> {
                loading.visible()
                content.gone()
                error.gone()
            }
            LoginViewState.ShowLoginForm -> {
                loading.gone()
                content.visible()
                error.gone()
            }
            LoginViewState.ShowLoginFormWithErrorState -> {
                loading.gone()
                content.visible()
                error.visible()
            }
        }
    }
}
