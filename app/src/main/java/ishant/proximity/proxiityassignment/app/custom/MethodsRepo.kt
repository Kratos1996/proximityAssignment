package ishant.proximity.proxiityassignment.app.custom

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityData
import java.math.RoundingMode
import java.text.DecimalFormat


class MethodsRepo(private  var context: Context
) {
    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS
    fun setBackGround(context: Context?, view: View, @DrawableRes drawable: Int) {
        view.background = ContextCompat.getDrawable(context!!, drawable)
    }

    fun hide(view: View){
        view.visibility=View.INVISIBLE
    }
    fun show(view: View){
        view.visibility=View.VISIBLE
    }
    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }


    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }
        val diff = now - time
        return if (diff < SECOND_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS.toDouble() ).toString()+ " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            ( diff / HOUR_MILLIS.toDouble()).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
            (diff / DAY_MILLIS.toDouble()).toString() + " days ago"
        }
    }

}