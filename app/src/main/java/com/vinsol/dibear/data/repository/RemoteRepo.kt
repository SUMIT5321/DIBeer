package com.vinsol.dibear.data.repository

import com.vinsol.dibear.data.entities.BeerData
import io.reactivex.Observable
import java.util.*

interface RemoteRepo {
    fun getTopBeers(): Observable<List<BeerData>>
    fun searchBeer(query: String): Observable<List<BeerData>>
    fun getBeer(beerId: Int): Observable<Optional<BeerData>>
}