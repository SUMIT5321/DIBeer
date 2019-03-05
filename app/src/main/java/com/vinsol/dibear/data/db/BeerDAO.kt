package com.vinsol.dibear.data.db

import androidx.room.*
import com.vinsol.dibear.data.entities.BeerData

@Dao
interface BeerDAO {

    @Query("SELECT * FROM beers")
    fun getFavoriteBeers(): List<BeerData>

    @Query("SELECT * FROM beers WHERE id=:beerId")
    fun getById(beerId: Int): BeerData?

    @Query("SELECT * FROM beers WHERE name LIKE :query")
    fun search(query: String): List<BeerData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBeer(beer: BeerData)

    @Delete
    fun deleteBeer(beer: BeerData)

    @Query("DELETE FROM beers")
    fun clear()
}