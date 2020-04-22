package com.arthur.marvelapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arthur.marvelapp.R
import com.arthur.marvelapp.util.load
import com.arthur.marvelapp.viewmodel.DetailsHeroesViewModel
import com.arthur.marvelapp.viewmodel.HeroesViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_detail_heroes.*
import kotlinx.android.synthetic.main.activity_heroes.*


class DetailHeroesActivity : AppCompatActivity(){

    private val viewModel: DetailsHeroesViewModel by lazy {
        ViewModelProviders.of(this).get(DetailsHeroesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_heroes)

        var idHeroe = intent.getIntExtra("idHeroe",0)

        details.visibility = View.GONE
        loadDetails.visibility = View.VISIBLE

        getData(idHeroe)
    }

    private fun getData(id : Int) {
        viewModel.getOneHeroes(id).observe(
            this, Observer {
                if (it.data == null) {
                    Snackbar.make(
                        detailHeroes,
                        it.error.toString(),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else {
                    it.data
                    imgPhotoHeroes.load(
                        "${it.data.data.results[0].thumbnail.path}/portrait_uncanny.${it.data.data.results[0].thumbnail.extension}",
                        imgPhotoHeroes
                    )
                    txtNameHeroes.text = it.data.data.results[0].name
                    txtDescriptionHeroes.text = it.data.data.results[0].description

                    details.visibility = View.VISIBLE
                    loadDetails.visibility = View.GONE
                }
            }
        )
    }
}
