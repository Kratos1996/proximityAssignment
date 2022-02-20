package ishant.proximity.proxiityassignment.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.base.BaseActivity
import ishant.proximity.proxiityassignment.databinding.ActivityMainBinding
import ishant.proximity.proxiityassignment.ui.home.viewmodel.HomeViewModel
import ishant.proximity.proxiityassignment.ui.splash.SplashFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setFragment(SplashFragment.getInstance()!!, false)
    }

}