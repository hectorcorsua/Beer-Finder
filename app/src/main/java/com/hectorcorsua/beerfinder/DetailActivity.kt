package com.hectorcorsua.beerfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hectorcorsua.beerfinder.models.Beer
import com.hectorcorsua.beerfinder.presenters.DetailActivityPresenter
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity(), DetailActivityPresenter.View {
    private lateinit var rootView : View
    private lateinit var presenter: DetailActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = DetailActivityPresenter(this)
        val beerId = intent.extras!!.getInt("beer")
        rootView = findViewById(R.id.detail_root)
        presenter.searchBeerById(beerId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showBeerDetail(beer: Beer) {
        supportActionBar?.title = beer.name
        Picasso.get()
            .load(beer.image)
            .into(findViewById<ImageView>(R.id.iv_detail_image))
        findViewById<TextView>(R.id.tv_detail_abv).text = beer.abv.toString()
        findViewById<TextView>(R.id.tv_detail_ibu).text = beer.ibu.toString()
        findViewById<TextView>(R.id.tv_detail_ebc).text = beer.ebc.toString()
        findViewById<TextView>(R.id.tv_detail_srm).text = beer.ebc.toString()
        findViewById<TextView>(R.id.tv_detail_ph).text = beer.ph.toString()
        findViewById<TextView>(R.id.tv_detail_description).text = beer.description
    }

    override fun showError(error: String) {

        Snackbar.make(this@DetailActivity,
            rootView,
            getString(R.string.get_beer_detail_error, error),
            BaseTransientBottomBar.LENGTH_LONG
        ).show()
    }
}