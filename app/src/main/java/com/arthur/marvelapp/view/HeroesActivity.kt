package com.arthur.marvelapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthur.marvelapp.R
import com.arthur.marvelapp.model.Character
import com.arthur.marvelapp.view.adapter.HeroesAdapter
import com.arthur.marvelapp.viewmodel.HeroesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_heroes.*


class HeroesActivity : AppCompatActivity(),
    HeroesAdapter.ItemClickListener {

    private val viewModel: HeroesViewModel by lazy {
        ViewModelProviders.of(this).get(HeroesViewModel::class.java)
    }

    private val adapter: HeroesAdapter by lazy {
        HeroesAdapter()
    }

    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes)

        progressBar.visibility = View.VISIBLE
        txtLoading.visibility = View.VISIBLE
        rvListHeroes.visibility = View.GONE

        val llm = LinearLayoutManager(this)
        rvListHeroes.layoutManager = llm
        rvListHeroes.adapter = adapter
        adapter.addItemClickListener(this)
        subscribeToList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("lmState", rvListHeroes.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    adapter.submitList(list)
                    progressBar.visibility = View.GONE
                    txtLoading.visibility = View.GONE
                    rvListHeroes.visibility = View.VISIBLE
                    if (recyclerState != null) {
                        rvListHeroes.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                }
            )
    }

    override fun onItemClick(character: Character?) {

        val intent = Intent(
            this,
            DetailHeroesActivity::class.java
        )
            .putExtra("idHeroe", character?.id)
        startActivity(intent)
    }
}
