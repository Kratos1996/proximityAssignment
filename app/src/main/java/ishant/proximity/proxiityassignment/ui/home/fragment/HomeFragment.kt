package ishant.proximity.proxiityassignment.ui.home.fragment

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
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.app.custom.MethodsRepo
import ishant.proximity.proxiityassignment.base.BaseActivity
import ishant.proximity.proxiityassignment.base.RetrySnackBarClickListener
import ishant.proximity.proxiityassignment.databinding.HomeFramgmentBinding
import ishant.proximity.proxiityassignment.databinding.SplashFramgmentBinding
import ishant.proximity.proxiityassignment.ui.chart.ChartFragment
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

class HomeFragment : Fragment(),RetrySnackBarClickListener,
    AirQualityListAdapter.onClickItemListner {
    private lateinit var binding: HomeFramgmentBinding
    private val homeViewModel by viewModel<HomeViewModel>()
    private val methods by inject<MethodsRepo>()
    private lateinit var adapter:AirQualityListAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.home_framgment, container, false)
        WorkStation()
        return binding.root
    }

    private fun WorkStation() {
        homeViewModel.connectSocketNow()
        adapter = AirQualityListAdapter(requireContext(),methods,this)
        adapter.UpdateList(ArrayList<AirQualityData>())
        binding.airPurifireRecycler.adapter = adapter
        binding.toolbar.back.setOnClickListener {
            (requireActivity() as BaseActivity).onBackPressed()
        }
        UpdateSocketData()
    }

    private fun UpdateSocketData() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.airQualityResponseFlow.collect { event ->
                when (event) {
                    is HomeViewModel.ResponseAirQualitySealed.loading -> {
                        if(event.isLoading){
                            methods.show(binding.progressBar)
                        }else{
                            methods.hide(binding.progressBar)
                        }
                    }
                    is HomeViewModel.ResponseAirQualitySealed.Success -> {
                        methods.hide(binding.progressBar)
                        if(event.response.airQualityResponseNew.isNotEmpty()){
                            adapter.UpdateList(ArrayList(event.response.airQualityResponseNew.toMutableList()))
                        }else{
                            (requireActivity() as BaseActivity).showCustomAlert(getString(R.string.data_not_found)
                                ,binding.root,getString(R.string.retry)
                                ,true
                                , listener = this@HomeFragment)
                        }
                    }
                    is HomeViewModel.ResponseAirQualitySealed.message ->{
                        (requireActivity() as BaseActivity).showCustomAlert(event.message,binding.root)
                    }
                    is HomeViewModel.ResponseAirQualitySealed.ErrorOnResponse -> {
                        methods.hide(binding.progressBar)
                        (requireActivity() as BaseActivity).showCustomAlert(event.message,binding.root,getString(
                            R.string.retry),true, listener = this@HomeFragment)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        methods.hide(binding.toolbar.back)
        binding.toolbar.ToolbarTitle.text=getString(R.string.home)
    }
    companion object {
        private var instance: HomeFragment? = null

        @JvmStatic
        fun getInstance(): HomeFragment? {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance
        }

    }

    override fun onClickRetry() {
        homeViewModel.connectSocketNow()
    }

    override fun onClick( data: AirQualityData) {
        (requireActivity() as BaseActivity).setFragment(ChartFragment.getInstance(data.city!!)!!,true)
    }
}