package com.hectorcorsua.beerfinder.presenters

import com.hectorcorsua.beerfinder.models.Beer
import com.hectorcorsua.beerfinder.services.BeerService
import com.hectorcorsua.beerfinder.services.callbacks.BeerCallback

class MainActivityPresenter(private var view: View) {

    private var page: Int = 1
    private val service = BeerService.getInstance()
    private var canLoadMore = false
    private var isLoadMore = false

    fun searchBeerByName(name : String?){
        isLoadMore = false
        page = 1
        if(name == null || name.isEmpty())
        {
            view.showBeerListResult(mutableListOf())
        } else {
            service.getBeers(page, name, beerCallback)
        }
    }

    fun loadMoreBeer(name : String){
        isLoadMore = true
        if(canLoadMore){
            page++
            service.getBeers(page, name, beerCallback)
        }
    }

    private val beerCallback : BeerCallback = object: BeerCallback {
        override fun onSuccess(result: List<Beer>?) {
            if (result != null) {
                if(isLoadMore)
                {
                    view.showMoreBeersResult(result)
                } else {
                    view.showBeerListResult(result)
                }
                canLoadMore = result.count() == 25
            }
        }

        override fun onError(error: String?) {
            view.showError(error!!)
        }

    }
    interface View{
        fun showMoreBeersResult(beers: List<Beer>)
        fun showBeerListResult(beers: List<Beer>)
        fun showError(error : String)
    }
}