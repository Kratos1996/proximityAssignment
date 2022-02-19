package ishant.proximity.proxiityassignment.base


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.ui.home.fragment.HomeFragment
import ishant.proximity.proxiityassignment.ui.splash.SplashFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    val CLICK_TIME = 1000L
    private var fragment: Fragment? = null
    private var doubleBackToExitPressedOnce = false
    private lateinit var snackBar: Snackbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    fun baseContext():Context{
        return this@BaseActivity
    }



    fun setFragment(resourceView: Int, fragment: Fragment, addToBackStackFlag: Boolean) {
        val mFragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val currentFragment = supportFragmentManager.findFragmentById(resourceView)

        if (currentFragment == null) {
            if (supportFragmentManager.fragments.size != 0) {
                if (addToBackStackFlag) {
                    mFragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
                }

                mFragmentTransaction.replace(resourceView, fragment)
                mFragmentTransaction.commit()
            }
        } else {
            if (!(currentFragment.javaClass.simpleName.equals(fragment.javaClass.simpleName))) {
                if (supportFragmentManager.fragments.size != 0) {
                    if (addToBackStackFlag) {
                        mFragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
                    }
                    mFragmentTransaction.replace(resourceView, fragment)
                    mFragmentTransaction.commit()
                }
            }

        }
    }

    /**
     * override onBackPressed
     *
     */
    @Override
    override fun onBackPressed() {
        val fm = supportFragmentManager
        val backStackCount = fm.backStackEntryCount

        if (getCurrentFragment() is SplashFragment) {
            finish()
        } else if (backStackCount > 1) {
            if (fragment is HomeFragment) {
                if (doubleBackToExitPressedOnce) {
                    finish()
                    return
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(baseContext(),getString(R.string.press_back_again),Toast.LENGTH_LONG).show()
                lifecycleScope.launch(Dispatchers.Default) {
                    delay(2000)
                    doubleBackToExitPressedOnce = false
                }
            } else {
                val backStackEntry = fm.getBackStackEntryAt(backStackCount - 1)
                val frag = fm.findFragmentByTag(backStackEntry.name)

                if (frag!!.childFragmentManager.backStackEntryCount > 1) {
                    frag.childFragmentManager.popBackStack()
                } else {
                    fm.popBackStack()
                }
            }

        } else {
            if (fragment is HomeFragment) {
                if (doubleBackToExitPressedOnce) {
                    finish()
                    return
                }
                doubleBackToExitPressedOnce = true
               Toast.makeText(baseContext(),getString(R.string.press_back_again),Toast.LENGTH_LONG).show()
                lifecycleScope.launch(Dispatchers.Default) {
                    delay(2000)
                    doubleBackToExitPressedOnce = false
                }
            } else {
                val backStackEntry = fm.getBackStackEntryAt(fm.backStackEntryCount - 1)
                val frag = fm.findFragmentByTag(backStackEntry.name)
                if (frag!!.childFragmentManager.backStackEntryCount > 1) {
                    frag.childFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        }
    }

    fun switchActivity( intent: Intent){
        startActivity(intent)
    }

    open fun showCustomAlert(
        msg: String?,
        v: View?,
        button: String?,
        isRetryOptionAvailable: Boolean,
        listener: RetrySnackBarClickListener
    ) {
        if (isRetryOptionAvailable) {
            snackBar = Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
                .setAction(button) { listener.onClickRetry() }
        } else {
            snackBar = Snackbar.make(v!!, msg!!, Snackbar.LENGTH_LONG)
        }
        snackBar.setActionTextColor(Color.BLUE)
        val snackBarView: View = snackBar.getView()
        val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }


    open fun getCurrentFragment(): Fragment? {
        fragment = supportFragmentManager.findFragmentById(R.id.container)
        return fragment
    }



}