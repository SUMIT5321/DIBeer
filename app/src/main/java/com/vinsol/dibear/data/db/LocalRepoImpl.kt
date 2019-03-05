package com.vinsol.dibear.data.db

import com.vinsol.dibear.data.entities.BeerData
import com.vinsol.dibear.data.repository.LocalRepo
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import java.util.*

class LocalRepoImpl(database: BeerDatabase): LocalRepo {

    private val dao: BeerDAO = database.getBeerDAO()

    override fun clear() {
        dao.clear()
    }

    override fun save(beerData: BeerData): Observable<Boolean> {
        return Observable.fromCallable {
            dao.saveBeer(beerData)
            true
        }
    }

    override fun delete(beerData: BeerData): Observable<Boolean> {
        return Observable.fromCallable {
            dao.deleteBeer(beerData)
            true
        }
    }

    override fun getAll(): Observable<List<BeerData>> {
        return Observable.fromCallable { dao.getFavoriteBeers() }
    }

    override fun get(beerId: Int): Observable<Optional<BeerData>> {
        return Observable.fromCallable {
            Optional.ofNullable(dao.getById(beerId))
        }
    }

    override fun search(query: String): Observable<List<BeerData>> {
        return Observable.fromCallable {
            dao.search(query)
        }
    }
}