package eren.dagli.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import eren.dagli.kotlincountries.R

fun ImageView.downloadImageFromApi(url:String?,placeHolderProgressBar:CircularProgressDrawable){

    val options=RequestOptions()
        .placeholder(placeHolderProgressBar)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .applyDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeHolderProgressBar(context:Context):CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth=10f
        centerRadius=40f
        start()
    }

/* 2. yol

fun ImageView.downloadImageFromApi(url:String?,placeHolderProgressBar: CircularProgressDrawable){

    val options=RequestOptions()
        .placeholder(placeHolderProgressBar)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .applyDefaultRequestOptions(options)
        .load(url)
        .into(this)

}*/
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView,url: String?){

    view.downloadImageFromApi(url, placeHolderProgressBar(view.context))
}