package eren.dagli.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import eren.dagli.kotlincountries.R
import eren.dagli.kotlincountries.databinding.FragmentCountryBinding
import eren.dagli.kotlincountries.util.downloadImageFromApi
import eren.dagli.kotlincountries.util.placeHolderProgressBar
import eren.dagli.kotlincountries.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {

    private lateinit var countryViewModel: CountryViewModel
    private var countryUuid=0
    private lateinit var dataBinding:FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root
        //return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid=CountryFragmentArgs.fromBundle(it).countryUUid
        }
        countryViewModel=ViewModelProviders.of(this).get(CountryViewModel::class.java)
        countryViewModel.getDataFromRoom(countryUuid)


        observeLiveData()
    }

   private fun observeLiveData(){

        countryViewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->

            country?.let {
                dataBinding.countryDetails=it


                /* countryName.setText(country.countryName)
                countryCapital.setText(country.countryCapital)
                countryCurrency.setText(country.countryCurrency)
                countryLanguage.setText(country.countryLanguage)
                countryRegion.setText(country.countryRegion)
                context?.let {

                    countryImage.downloadImageFromApi(country.imageUrl, placeHolderProgressBar(it))

                }*/
            }
        })
            }

}
