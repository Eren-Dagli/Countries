package eren.dagli.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import eren.dagli.kotlincountries.R
import eren.dagli.kotlincountries.adapter.CountryAdapter
import eren.dagli.kotlincountries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private val countryAdapter=CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        feedViewModel=ViewModelProviders.of(this).get(FeedViewModel::class.java)
        feedViewModel.refreshData()

        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=countryAdapter


        swipeRefreshLayout.setOnRefreshListener {

            countryLoading.visibility=View.VISIBLE
            countryList.visibility=View.GONE
            countryError.visibility=View.GONE
            feedViewModel.refreshDataFromApi()
            swipeRefreshLayout.isRefreshing=false
        }


        observeLiveData()

    }

    private fun observeLiveData(){

        feedViewModel.countries.observe(viewLifecycleOwner, Observer {

            it?.let {

                countryList.visibility=View.VISIBLE
                countryAdapter.updateCountryList(it)

            }
        })

        feedViewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE
                    countryError.visibility = View.GONE
                    countryList.visibility=View.GONE
                }else{
                    countryLoading.visibility=View.GONE
                }
            }
        })

        feedViewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {

                    countryError.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryLoading.visibility = View.GONE
                }else{
                    countryError.visibility=View.GONE
                }

            }
        })
    }
}