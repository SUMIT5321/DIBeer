package com.vinsol.dibear.data.api

import com.vinsol.dibear.data.entities.BeerData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeerApi {

    @GET("/v2/beers/{id}")
    fun getBeerDetails(@Path("id") beerId: Int): Observable<List<BeerData>>

    @GET("/v2/beers")
    fun getTopBeers(): Observable<List<BeerData>>

    @GET("/v2/beers")
    fun searchBeers(@Query("beer_name") query: String): Observable<List<BeerData>>
}

const val beerBaseUrl = "https://api.punkapi.com"