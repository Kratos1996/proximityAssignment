package ishant.proximity.proxiityassignment.ui.home.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.app.custom.MethodsRepo
import ishant.proximity.proxiityassignment.base.BaseAdapter
import ishant.proximity.proxiityassignment.base.BaseViewHolder
import ishant.proximity.proxiityassignment.databinding.AirQualityItemBinding
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityData
import java.util.ArrayList

class AirQualityListAdapter (context: Context,val methods:MethodsRepo,val listner:onClickItemListner) :
    BaseAdapter<AirQualityData, AirQualityItemBinding>(context, R.layout.air_quality_item) {

    override fun onViewHolderBind(
        viewHolder: BaseViewHolder<AirQualityItemBinding>,
        binding: AirQualityItemBinding,
        position: Int,
        data: AirQualityData,
        list:ArrayList<AirQualityData>
    ) {

        binding.cityAQI.text=methods.roundOffDecimal(data.aqi!!).toString()
        binding.lastUpdate.text=methods.getTimeAgo(data.time!!)
        when (data.aqi) {
            in 0.0..50.0 -> {
                //good
                binding.cityName.text=data.city+" (Good)"
                methods.setBackGround(context,binding.changeBackground,R.color.good)
                binding.cityName.setTextColor(context.getColor(R.color.white))
                binding.cityAQI.setTextColor(context.getColor(R.color.white))
                binding.lastUpdate.setTextColor(context.getColor(R.color.white))
            }
            in 51.0..100.0 -> {
                //satisfactory
                binding.cityName.text=data.city+" (Satisfactory)"
                methods.setBackGround(context,binding.changeBackground,R.color.satisfactory)
                binding.cityName.setTextColor(context.getColor(R.color.white))
                binding.cityAQI.setTextColor(context.getColor(R.color.white))
                binding.lastUpdate.setTextColor(context.getColor(R.color.white))
            }
            in 101.0..200.0 -> {
                //Moderate
                binding.cityName.text=data.city+" (Moderate)"
                methods.setBackGround(context,binding.changeBackground,R.color.moderate)
                binding.cityName.setTextColor(context.getColor(R.color.black))
                binding.cityAQI.setTextColor(context.getColor(R.color.black))
                binding.lastUpdate.setTextColor(context.getColor(R.color.black))
            }
            in 201.0..300.0 -> {
                //poor
                binding.cityName.text=data.city+" (Poor)"
                methods.setBackGround(context,binding.changeBackground,R.color.poor)
                binding.cityName.setTextColor(context.getColor(R.color.white))
                binding.cityAQI.setTextColor(context.getColor(R.color.white))
                binding.lastUpdate.setTextColor(context.getColor(R.color.white))
            }
            in 301.0..400.0 -> {
                //very poor
                binding.cityName.text=data.city+" (Very Poor)"
                methods.setBackGround(context,binding.changeBackground,R.color.verypoor)
                binding.cityName.setTextColor(context.getColor(R.color.white))
                binding.cityAQI.setTextColor(context.getColor(R.color.white))
                binding.lastUpdate.setTextColor(context.getColor(R.color.white))
            }
            in 401.0..500.0 -> {
                //severe
                binding.cityName.text=data.city+" (Severe)"
                methods.setBackGround(context,binding.changeBackground,R.color.severe)
                binding.cityName.setTextColor(context.getColor(R.color.white))
                binding.cityAQI.setTextColor(context.getColor(R.color.white))
                binding.lastUpdate.setTextColor(context.getColor(R.color.white))
            }
        }
        binding.root.setOnClickListener {listner.onClick( data = data)}
    }
    interface onClickItemListner{
        fun onClick(data: AirQualityData)
    }
}