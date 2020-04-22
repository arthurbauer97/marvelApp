package com.arthur.marvelapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.arthur.marvelapp.api.MarvelApi
import com.arthur.marvelapp.model.Character
import com.arthur.marvelapp.paging.HeroesDataSourceFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HeroesViewModel : ViewModel() {

    lateinit var marvelApi: MarvelApi

    var characterList: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory: HeroesDataSourceFactory


    init {
        sourceFactory = HeroesDataSourceFactory(compositeDisposable, MarvelApi.getService())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
