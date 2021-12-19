package com.hectorcorsua.beerfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.hectorcorsua.beerfinder.adapters.BeerAdapter
import com.hectorcorsua.beerfinder.adapters.OnBeerClickListener
import com.hectorcorsua.beerfinder.models.Beer
import com.hectorcorsua.beerfinder.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    private lateinit var adapter: BeerAdapter
    private val beerList = mutableListOf<Beer>()
    private lateinit var beerRecyclerView : RecyclerView
    private lateinit var presenter: MainActivityPresenter
    private lateinit var edtSearchName : EditText
    private val linearLayoutManager = LinearLayoutManager(this)
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()
    private lateinit var scrollListener: RecyclerView.OnScrollListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this)
        edtSearchName = findViewById(R.id.edt_search)
        edtSearchName.addTextChangedListener(textWatcher)
        beerRecyclerView = findViewById(R.id.rv_beers)
        prepareRecycler()
    }

    private fun prepareRecycler()
    {
        adapter = BeerAdapter(beerList, onListItemClickListener)
        beerRecyclerView.layoutManager = linearLayoutManager
        beerRecyclerView.adapter = adapter
        setRecyclerViewScrollListener()
    }

    private val textWatcher : TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            presenter.searchBeerByName(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private val onListItemClickListener =  object : OnBeerClickListener {
        override fun onElementClick(beer: Beer) {
            val intent = Intent(this@MainActivity,DetailActivity::class.java)
            intent.putExtra("beer",beer.id)
            startActivity(intent)
        }
    }
    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    presenter.loadMoreBeer(edtSearchName.text.toString())
                    beerRecyclerView.removeOnScrollListener(scrollListener)
                }
            }
        }

    }

    override fun showMoreBeersResult(beers: List<Beer>) {
        runOnUiThread {
            beerList.addAll(beers)
            adapter.notifyDataSetChanged()
        }
        beerRecyclerView.addOnScrollListener(scrollListener)
    }

    override fun showBeerListResult(beers: List<Beer>) {
        runOnUiThread {
            beerList.clear()
            beerList.addAll(beers)
            adapter.notifyDataSetChanged()
        }
        beerRecyclerView.addOnScrollListener(scrollListener)
    }

    override fun showError(error: String) {
        Snackbar.make(this@MainActivity,
            beerRecyclerView,
            getString(R.string.get_beer_list_error,error),
            LENGTH_LONG)
            .show()
    }
}