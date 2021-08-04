package com.hannesdorfmann.navigation.view.iap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hannesdorfmann.navigation.OnBackPressed
import com.hannesdorfmann.navigation.databinding.FragmentPurchaseBinding
import com.hannesdorfmann.navigation.utils.getViewModel

open class IapNewsFragment : Fragment(), OnBackPressed {

    private lateinit var vm: IapViewModel
    private lateinit var bindingIap: FragmentPurchaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bindingIap = FragmentPurchaseBinding.inflate(inflater, container, false)
        return bindingIap.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = getViewModel()
        bindingIap.purchase.setOnClickListener {
            vm.onBuyingClicked()
        }
    }

    override fun onBackPressed(): Boolean {
        vm.onBackPressed()
        return true
    }
}