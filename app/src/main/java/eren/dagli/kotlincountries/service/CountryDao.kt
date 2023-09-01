package eren.dagli.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import eren.dagli.kotlincountries.model.Country

@Dao
interface CountryDao {

    // Data Access Object

    @Insert()
    suspend fun insertAll(vararg countries:Country):List<Long>

    // vararg -> birden fazla country koyabilmek için kullanılır. Kaç tane koyulacağını kendi ayarlar

    @Query("SELECT * FROM country")
    suspend fun getAllCountries():List<Country>

    @Query(value = "SELECT * FROM country WHERE uuid=:countryId")
    suspend fun getCountry(countryId:Int):Country

    @Query("DELETE FROM country")
    suspend fun deleteALlCountries()
}