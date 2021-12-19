package com.hectorcorsua.beerfinder.services.callbacks

import com.hectorcorsua.beerfinder.models.Beer

interface BeerCallback {

    fun onSuccess(result: List<Beer>?)

    fun onError(error: String?)
}