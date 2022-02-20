package ishant.proximity.proxiityassignment.ui.chart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.app.custom.MethodsRepo
import ishant.proximity.proxiityassignment.base.BaseActivity
import ishant.proximity.proxiityassignment.base.RetrySnackBarClickListener
import ishant.proximity.proxiityassignment.databinding.ChartFramgmentBinding
import ishant.proximity.proxiityassignment.databinding.HomeFramgmentBinding
import ishant.proximity.proxiityassignment.databinding.SplashFramgmentBinding
import ishant.proximity.proxiityassignment.ui.home.activity.MainActivity
import ishant.proximity.proxiityassignment.ui.home.adapter.AirQualityListAdapter
import ishant.proximity.proxiityassignment.ui.home.viewmodel.HomeViewModel
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ChartFragment : Fragment(),RetrySnackBarClickListener
{
    private lateinit var binding: ChartFramgmentBinding
    private val homeViewModel by viewModel<HomeViewModel>()
    private val methods by inject<MethodsRepo>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.chart_framgment, container, false)
        WorkStation()
        return binding.root
    }

    private fun WorkStation() {
        homeViewModel.connectSocketNow()
        binding.toolbar.back.setOnClickListener {
            (requireActivity() as BaseActivity).onBackPressed()
        }
        UpdateSocketData()
    }

    private fun UpdateSocketData() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.airQualityResponseFlow.collect { event ->
                when (event) {

                    is HomeViewModel.ResponseAirQualitySealed.Success -> {
                        if(event.response.airQualityResponseNew.isNotEmpty()){
                            homeViewModel.cityAqiList(ArrayList(event.response.airQualityResponseNew),city = city!!)
                            val chart = AAChartModel()
                                .chartType(AAChartType.Column)
                                .title(city+" "+getString(R.string.air_quality_chart))
                                .dataLabelsEnabled(true)
                                .series(
                                    arrayOf(
                                        AASeriesElement()
                                            .name(city)
                                            .data(homeViewModel.CitAqiList.toTypedArray())
                                    )
                                )

                            binding.airDataChart.aa_drawChartWithChartModel(chart)
                        }else{
                            (requireActivity() as BaseActivity).showCustomAlert(getString(R.string.data_not_found)
                                ,binding.root,getString(R.string.retry)
                                ,true
                                , listener = this@ChartFragment)
                        }
                    }
                    is HomeViewModel.ResponseAirQualitySealed.message ->{
                        (requireActivity() as BaseActivity).showCustomAlert(event.message,binding.root)
                    }
                    is HomeViewModel.ResponseAirQualitySealed.ErrorOnResponse -> {
                        (requireActivity() as BaseActivity).showCustomAlert(event.message
                            ,binding.root
                            ,getString(
                            R.string.retry)
                            ,true
                            , listener = this@ChartFragment)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        methods.show(binding.toolbar.back)
        binding.toolbar.ToolbarTitle.text=city+" "+getString(R.string.air_quality_chart)
    }
    companion object {
        private var instance: ChartFragment? = null
        private var city:String? = null

        @JvmStatic
        fun getInstance(cityName:String): ChartFragment? {
            instance = ChartFragment()
            city=cityName
            return instance
        }

    }

    override fun onClickRetry() {
        homeViewModel.connectSocketNow()
    }


}