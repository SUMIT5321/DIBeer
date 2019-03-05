package com.vinsol.dibear.data.api

import com.vinsol.dibear.data.entities.BeerData
import com.vinsol.dibear.data.repository.RemoteRepo
import io.reactivex.Observable
import java.util.*

class RemoteRepoImpl(private val api: BeerApi): RemoteRepo {

    override fun getTopBeers(): Observable<List<BeerData>> {
        return api.getTopBeers()
    }

    override fun searchBeer(query: String): Observable<List<BeerData>> {
        return api.searchBeers(query)
    }

    override fun getBeer(beerId: Int): Observable<Optional<BeerData>> {
        return api.getBeerDetails(beerId = beerId).map { beerList ->
            if(beerList.isEmpty()) Optional.empty()
            else Optional.of(beerList[0])
        }
    }
}