package com.hannesdorfmann.navigation.view.onboarding.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hannesdorfmann.navigation.databinding.FragmentWelcomeBinding
import com.hannesdorfmann.navigation.utils.getViewModel

class WelcomeFragment : Fragment() {

    private lateinit var bindingWelcome: FragmentWelcomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        bindingWelcome = FragmentWelcomeBinding.inflate(inflater, container, false)
        return bindingWelcome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm: WelcomeViewModel = getViewModel()
        with(bindingWelcome) {
            vm.username.observe(viewLifecycleOwner) {
                name.text = "Hey $it"
            }
            next.setOnClickListener {
                vm.onNextClicked()
            }
        }
    }
}