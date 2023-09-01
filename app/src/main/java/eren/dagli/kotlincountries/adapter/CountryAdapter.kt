package eren.dagli.kotlincountries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import eren.dagli.kotlincountries.R
import eren.dagli.kotlincountries.databinding.ItemCountryBinding
import eren.dagli.kotlincountries.model.Country
import eren.dagli.kotlincountries.util.downloadImageFromApi
import eren.dagli.kotlincountries.util.placeHolderProgressBar
import eren.dagli.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList:ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CounrtyViewHolder>(),CountryClickListener {

    class CounrtyViewHolder(val view:ItemCountryBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounrtyViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        //val view=inflater.inflate(R.layout.item_country,parent,false)
        val view=DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CounrtyViewHolder(view)

    }

    override fun onBindViewHolder(holder: CounrtyViewHolder, position: Int) {
        holder.view.country=countryList[position]

        holder.view.listener=this
        /*holder.itemView.name.text=countryList.get(position).countryName
        holder.itemView.region.text=countryList[position].countryRegion

        holder.itemView.imageView.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryUUid = countryList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        // Download Images

        holder.itemView.imageView.downloadImageFromApi(countryList[position].imageUrl,
            placeHolderProgressBar(holder.itemView.context)
        )*/
    }

    override fun getItemCount(): Int {
        return countryList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList:List<Country>){

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClick(view: View) {
        val uuid=view.countryUuid.text.toString().toInt()
        val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }
}