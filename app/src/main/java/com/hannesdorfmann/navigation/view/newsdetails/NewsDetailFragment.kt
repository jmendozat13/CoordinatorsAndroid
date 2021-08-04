package com.hannesdorfmann.navigation.view.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hannesdorfmann.navigation.OnBackPressed
import com.hannesdorfmann.navigation.databinding.FragmentDetailBinding
import com.hannesdorfmann.navigation.utils.getViewModel

class NewsDetailFragment : Fragment(), OnBackPressed {

    private lateinit var bindingNewDetail: FragmentDetailBinding

    companion object {
        private const val KEY_NEWS_ID = "NewsId"
        fun newInstance(newsId: Int): NewsDetailFragment {
            val b = Bundle()
            b.putInt(KEY_NEWS_ID, newsId)
            val f = NewsDetailFragment()
            f.arguments = b
            return f
        }
    }

    private var newsId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsId = requireArguments().getInt(KEY_NEWS_ID)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingNewDetail = FragmentDetailBinding.inflate(inflater, container, false)
        return bindingNewDetail.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm: NewsDetailViewModel = getViewModel()
        vm.newsId = newsId
        vm.title.observe(viewLifecycleOwner) {
            bindingNewDetail.title.text = it
        }
    }


    override fun onBackPressed(): Boolean {
        val vm: NewsDetailViewModel = getViewModel()
        vm.closeNews()
        return true
    }
}