package eren.dagli.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eren.dagli.kotlincountries.model.Country
import eren.dagli.kotlincountries.service.CountryAPIService
import eren.dagli.kotlincountries.service.CountryDatabase
import eren.dagli.kotlincountries.util.CustomSharedPreferences
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {

    private val countryApiService=CountryAPIService()
    private val disposable=CompositeDisposable()
    private var customSharedPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime= 10 * 0.6 * 1000 * 1000 * 1000L

    val countries=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()


    fun refreshData(){

        val updateTime=customSharedPreferences.getTime()

        if (updateTime!=null && updateTime!=0L && System.nanoTime()- updateTime < refreshTime){

            getDataFromSQLite()
        }else {
            getDataFromAPI()
        }
    }

    fun refreshDataFromApi(){
        getDataFromAPI()
    }
    fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showContries(countries)
            Toast.makeText(getApplication(),"Countries from SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value=true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                        storeInSqlite(t)
                        Toast.makeText(getApplication(),"Countries from Api",Toast.LENGTH_LONG).show()
                    }
                    override fun onError(e: Throwable) {

                        countryLoading.value=false
                        countryError.value=true
                        e.printStackTrace()
                    }
                })
        )
    }
    private fun showContries(countryList:List<Country>){

        countries.value=countryList
        countryLoading.value=false
        countryError.value=false
    }

        private fun storeInSqlite(list:List<Country>){

            launch {

                val dao=CountryDatabase(getApplication()).countryDao()

                dao.deleteALlCountries()

                val listLong=dao.insertAll(*list.toTypedArray())

                var i=0

                while (i<list.size){

                    list[i].uuid=listLong[i].toInt()

                    i+=1
                }
                showContries(list)
            }
            customSharedPreferences.saveTime(System.nanoTime())
        }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}