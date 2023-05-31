package com.messenger.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.messenger.R
import com.messenger.databinding.ActivityLayoutBinding
import com.messenger.domain.type.Failure
import com.messenger.presentation.Authenticator
import com.messenger.ui.core.PermissionManager
import com.messenger.ui.fragment.TabsFragment
import javax.inject.Inject

class AppActivity : AppCompatActivity() {

    private var _binding: ActivityLayoutBinding? = null
    private val binding: ActivityLayoutBinding
        get() = _binding ?: throw RuntimeException("ActivityLayoutBinding == null")

    private var navController: NavController? = null
    private val topLevelDestinations = setOf(getLoginDestination(), getNavigationDestination(), getChatsDestination())
    private val mainDestinations = setOf(getLoginDestination(), getNavigationDestination(), getChatsDestination(), getFriendsnDestination(), getProfileDestination())

    var bottomNavigationView: BottomNavigationView? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var authentificator: Authenticator

    @Inject
    lateinit var permissionManager: PermissionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLayoutBinding.inflate(layoutInflater)

        App.appComponent.inject(this)

        val splash = installSplashScreen()
        setContentView(binding.root)

        supportActionBar?.hide()

        observeSplashScreenVisibility(splash)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        //todo
        if (isStartDestination(navController?.currentDestination)) {
            super.onBackPressed()
        } else {
            navController?.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //todo
        //fragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //permissionManager.requestObject?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun observeSplashScreenVisibility(splash: SplashScreen) {
        splash.setKeepOnScreenCondition{ true }

        binding.root.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {

                    authentificator.userLoggedIn().let {
                        setupStartDestination(it)
                        binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                        splash.setKeepOnScreenCondition{ false }
                        return true
                    }

                    return false
                }
            }
        )
    }


    // fragment listener is sued for tracking current nav controller
    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)

            bottomNavigationView?.let {
                it.visibility = if(mainDestinations.contains(navController?.currentDestination?.id ?: 0)) View.VISIBLE else View.GONE
            }

            if (f is TabsFragment || f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController = navController
    }

    private fun setupStartDestination(isSingIn: Boolean) {
        val navController = getRootNavController()
        val graph = navController.navInflater.inflate(getMainNavigationGraphId())

        if (isSingIn) {
            graph.setStartDestination(getNavigationDestination())
        } else {
            graph.setStartDestination(getLoginDestination())
        }

        navController.graph = graph
        this.navController = navController
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        return topLevelDestinations.contains(destination.id)
    }


    private fun getRootNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        return navHost.navController
    }


    fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    fun progressStatus(viewStatus: Int) {
        //toolbar_progress_bar.visibility = viewStatus
    }


    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            is Failure.NetworkConnectionError -> showMessage(getString(R.string.error_network))
            is Failure.ServerError -> showMessage(getString(R.string.error_server))
            is Failure.EmailAlreadyExistError -> showMessage(getString(R.string.error_email_already_exist))
            is Failure.AuthError -> showMessage(getString(R.string.error_auth))
            is Failure.TokenError -> {
                val builder = NavOptions.Builder()
                val options =
                    builder.setPopUpTo(getRootNavController().graph.startDestinationId, true)
                        .build()
                getRootNavController().navigate(R.id.action_global_loginFragment, null, options)
            }
            is Failure.AlreadyFriendError -> showMessage(getString(R.string.error_already_friend))
            is Failure.AlreadyRequestedFriendError -> showMessage(getString(R.string.error_already_requested_friend))
            is Failure.FilePickError -> showMessage(getString(R.string.error_picking_file))
            else -> {}
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun getLoginDestination(): Int = R.id.loginFragment

    private fun getNavigationDestination(): Int = R.id.tabsFragment

    private fun getMainNavigationGraphId(): Int = R.navigation.main_navigation

    private fun getFriendsnDestination(): Int = R.id.friendsFragment

    private fun getChatsDestination(): Int = R.id.chatsFragment

    private fun getProfileDestination(): Int = R.id.profileFragment
}


inline fun Activity?.base(block: AppActivity.() -> Unit) {
    (this as AppActivity).let(block)
}