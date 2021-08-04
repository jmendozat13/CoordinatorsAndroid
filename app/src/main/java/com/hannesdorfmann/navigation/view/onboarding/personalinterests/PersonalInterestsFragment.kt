package com.hannesdorfmann.navigation.view.onboarding.personalinterests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.navigation.databinding.FragmentCategoriesBinding
import com.hannesdorfmann.navigation.utils.getViewModel

class PersonalInterestsFragment : Fragment() {

    lateinit var adapter: PersonalInteresstsAdapter
    private lateinit var bindingCategories: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingCategories = FragmentCategoriesBinding.inflate(inflater, container, false)
        return bindingCategories.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: PersonalInterestsViewModel = getViewModel()

        adapter = PersonalInteresstsAdapter(layoutInflater) {
            viewModel.toggleSelected(it)
        }

        with(bindingCategories) {
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = adapter
            nextButton.setOnClickListener { viewModel.nextClicked() }
        }
        viewModel.allCategories.observe(viewLifecycleOwner) {
            adapter.items = it
        }

        viewModel.selectedItems.observe(viewLifecycleOwner) {
            adapter.selected = it
        }
    }

}