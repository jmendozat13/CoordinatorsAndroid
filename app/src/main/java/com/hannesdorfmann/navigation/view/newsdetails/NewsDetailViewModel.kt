package com.hannesdorfmann.navigation.view.newsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hannesdorfmann.navigation.domain.news.NewsRepository
import io.reactivex.disposables.Disposable

class NewsDetailViewModel(
        private val newsRepository: NewsRepository,
        private val onCloseNews: () -> Unit
) : ViewModel() {

    private val mTitle = MutableLiveData<String>()
    val title: LiveData<String> get() = mTitle

    lateinit var disposable: Disposable

    var newsId: Int = 0
        set(value) {
            field = value
            disposable = newsRepository.getNewsArticle(newsId).subscribe {
                mTitle.value = it.title
            }
        }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun closeNews() {
        onCloseNews()
    }

}