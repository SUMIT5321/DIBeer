package com.vinsol.dibear.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "beers")
data class BeerData(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("tagline") val tagLine: String,
    @SerializedName("first_brewed") val firstBrewed: String,
    @SerializedName("description") val description: String,
    @SerializedName("abv") val abv: Double,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("food_pairing") val foodPairing: List<String>,
    @SerializedName("brewers_tips") val brewersTips: String,
    @SerializedName("contributed_by") val contributedBy: String,
    @SerializedName("isFavorite") var isFavorite: Boolean = false
)