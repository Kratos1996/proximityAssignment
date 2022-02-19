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
import ishant.proximity.proxiityassignment.databinding.SplashFramgmentBinding
import ishant.proximity.proxiityassignment.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: SplashFramgmentBinding
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.splash_framgment, container, false)
        WorkStation()
        return binding.root
    }

    private fun WorkStation() {

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
}