package ishant.proximity.proxiityassignment.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.base.BaseActivity
import ishant.proximity.proxiityassignment.ui.home.viewmodel.HomeViewModel
import ishant.proximity.proxiityassignment.ui.splash.SplashFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(R.layout.splash_framgment,SplashFragment.getInstance()!!,false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}