package com.vinsol.dibear.data.repository

import com.vinsol.dibear.data.entities.BeerData
import io.reactivex.Observable
import java.util.*

interface LocalRepo {
    fun clear()
    fun save(beerData: BeerData): Observable<Boolean>
    fun delete(beerData: BeerData): Observable<Boolean>
    fun getAll(): Observable<List<BeerData>>
    fun get(beerId: Int): Observable<Optional<BeerData>>
    fun search(query: String): Observable<List<BeerData>>
}