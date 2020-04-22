package com.arthur.marvelapp.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.arthur.marvelapp.api.MarvelApi
import com.arthur.marvelapp.viewmodel.HeroesViewModel
import com.arthur.marvelapp.model.Character
import io.reactivex.disposables.CompositeDisposable

class HeroesDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: MarvelApi
) : PageKeyedDataSource<Int, Character>() {

    var ts = "1587169291"
    var apikey = "0389342af8aecdff040fc2aca71a2b3a"
    var hash = "db7eff7924ae6fa28bf493dab22be5d2"

    lateinit var viewModel: HeroesViewModel

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }


    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Character>?,
                                 callback: LoadCallback<Int, Character>?) {
        compositeDisposable.add(
            marvelApi.allCharacters(ts,apikey,hash,requestedPage * requestedLoadSize)
                .subscribe(
                    { response ->
                        Log.d("NGVL", "Loading page: $requestedPage")
                        initialCallback?.onResult(response.data.results, null, adjacentPage)
                        callback?.onResult(response.data.results, adjacentPage)
                    },
                    { t ->
                        Log.d("NGVL", "Error loading page: $requestedPage", t)
                    }
                )
        );
    }
}