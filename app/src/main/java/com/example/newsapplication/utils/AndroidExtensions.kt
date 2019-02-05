package com.example.newsapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapplication.R
import com.example.newsapplication.view.ui.MainActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.text.ParseException

fun isNetworkStatusAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        it.activeNetworkInfo?.let {
            if (it.isConnected) return true
        }
    }
    return false
}


fun ImageView.loadImg(imageUrl: String) =
    Glide.with(context)
        .load(imageUrl)
        .into(this)

/*
fun MainActivity.waitForConnection() {
    this.waitForNetwork = ReactiveNetwork.observeInternetConnectivity()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ connected ->
            if (connected!!) {
                this.swapToNewsFragment()
                subscriptions.remove(this.waitForNetwork)
            }

        }
        ) { throwable -> UiUtils.handleThrowable(throwable) }
    this.subscriptions.add(this.waitForNetwork)
}
*/

fun String.getElapsedTimeString():String{
    var startTime= DateTime()
    try {
        startTime = ISODateTimeFormat.dateTimeParser().parseDateTime(this)
    }
    catch (exception: ParseException){
        Log.e("Time parse error: ", exception.message)
        return ""
    }
    val endTime = DateTime()
    var elapsedTime:String = ""
    var difference = endTime.millis.minus(startTime.millis)

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = difference / daysInMilli
    if(elapsedDays == 0.toLong()){

        difference %= daysInMilli
        val elapsedHours = difference / hoursInMilli
        if (elapsedHours == 0.toLong()){

            difference %= hoursInMilli
            val elapsedMinutes = difference / minutesInMilli
            if ((elapsedMinutes == 0.toLong()).or(elapsedMinutes == 1.toLong()) ){
                elapsedTime= "A minute ago "
            }
            else{
                elapsedTime= "${elapsedMinutes.toString()} minutes ago"
            }

        }
        else{
            when(elapsedHours){
                1.toLong() -> elapsedTime= "${elapsedHours.toString()} hour ago"
                else -> elapsedTime= "${elapsedHours.toString()} hours ago"
            }
        }

    }
    else{
        when(elapsedDays){
            1.toLong() -> elapsedTime= "${elapsedDays.toString()} day ago"
            else -> elapsedTime= "${elapsedDays.toString()} days ago"
        }
    }

    return elapsedTime
}