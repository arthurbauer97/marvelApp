package com.arthur.marvelapp.paging

import androidx.paging.DataSource
import com.arthur.marvelapp.api.MarvelApi
import com.arthur.marvelapp.model.Character
import io.reactivex.disposables.CompositeDisposable

class HeroesDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: MarvelApi
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return HeroesDataSource(compositeDisposable,marvelApi)
    }
}