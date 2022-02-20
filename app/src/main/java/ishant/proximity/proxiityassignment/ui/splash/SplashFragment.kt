package ishant.proximity.proxiityassignment.ui.splash

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
import ishant.proximity.proxiityassignment.base.BaseActivity
import ishant.proximity.proxiityassignment.databinding.SplashFramgmentBinding
import ishant.proximity.proxiityassignment.ui.home.fragment.HomeFragment
import ishant.proximity.proxiityassignment.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(), Animation.AnimationListener {
    private lateinit var binding: SplashFramgmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.splash_framgment, container, false)
        binding.logo.visibility= View.VISIBLE
        val myAnim2 = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.logo.startAnimation(myAnim2)
        myAnim2.setAnimationListener(this)
        return binding.root
    }

    override fun onAnimationStart(animation: Animation?) {

    }


    override fun onAnimationEnd(animation: Animation?) {

        lifecycleScope.launch (Dispatchers.Main){
            delay(2000L)
            (requireActivity() as BaseActivity).setFragment(HomeFragment.getInstance()!!,true)
        }

    }

    override fun onAnimationRepeat(animation: Animation?) {

    }
    companion object {
        private var instance: SplashFragment? = null

        @JvmStatic
        fun getInstance(): SplashFragment? {
            if (instance == null) {
                instance = SplashFragment()
            }
            return instance
        }

    }
}