package com.hectorcorsua.beerfinder.presenters

import com.hectorcorsua.beerfinder.models.Beer
import com.hectorcorsua.beerfinder.services.BeerService
import com.hectorcorsua.beerfinder.services.callbacks.BeerCallback

class DetailActivityPresenter(private var view: View) {

    private val service = BeerService.getInstance()

    fun searchBeerById(beerId : Int){
        service.getBeerDetail(beerId,beerDetailCallback)
    }

    private val beerDetailCallback : BeerCallback = object: BeerCallback {
        override fun onSuccess(result: List<Beer>?) {
            if (result != null) {
                view.showBeerDetail(result.first())
            }
        }

        override fun onError(error: String?) {
            view.showError(error!!)
        }

    }

    interface View{
        fun showBeerDetail(beer: Beer)
        fun showError(error : String)
    }
}