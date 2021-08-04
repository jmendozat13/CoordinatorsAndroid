package com.hannesdorfmann.navigation.view.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hannesdorfmann.navigation.domain.ab.AbTest
import com.hannesdorfmann.navigation.domain.news.News
import com.hannesdorfmann.navigation.domain.news.NewsRepository
import com.hannesdorfmann.navigation.domain.user.Usermanager
import io.reactivex.disposables.Disposable

class NewsListViewModel(
        newsRepository: NewsRepository,
        private val userManager: Usermanager,
        private val abTest: AbTest,
        private var onItemSelected: ((Int) -> Unit)?
) : ViewModel() {

    private val disposable: Disposable

    private val mItems = MutableLiveData<List<News>>()
    val items: LiveData<List<News>> get() = mItems

    init {
        disposable = newsRepository.getNewsArticles().subscribe { mItems.value = it }
    }

    fun itemSelected(itemId: Int) {
        onItemSelected!!(itemId)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        onItemSelected = null
    }

    fun logout() {
        userManager.logout().blockingAwait()
    }

    fun toggleABTestGroupAssignment() {
        abTest.toggleAssignedTestGroup()
    }
}