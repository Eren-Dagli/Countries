package eren.dagli.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eren.dagli.kotlincountries.model.Country
import eren.dagli.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application ) {

    val countryLiveData=MutableLiveData<Country>()


    fun getDataFromRoom(uuid:Int){

        launch {

            val dao=CountryDatabase(getApplication()).countryDao()
            val countryId=dao.getCountry(uuid)
            countryLiveData.value=countryId
        }
    }

}