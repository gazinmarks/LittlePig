package br.com.littlepig.presentation.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.littlepig.R
import br.com.littlepig.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    private val drawer: DrawerLayout by lazy {
        DrawerLayout(applicationContext)
    }
    private val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        setupNavigation()
        configureToolbar()
        setDrawer()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun configureToolbar() = with(binding.toolbar) {
        if (isLoginPage()) {
            toolbarCustom.visibility = View.GONE
        }
    }

    private fun isLoginPage() = navController.currentDestination?.id == R.id.loginPageFragment

    private fun setDrawer() = with(binding) {
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        toolbar.iconDrawer.setOnClickListener {
            drawer.open()
        }

        drawerWindow.setNavigationItemSelectedListener {// TODO fazer aba home iniciar ja selecionada
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homePageFragment)
                    drawer.closeDrawers()
                    true
                }

                R.id.register_transaction -> {
                    navController.navigate(R.id.createTransactionPageFragment)
                    drawer.closeDrawers()
                    true
                }

                else -> true
            }
        }
    }
}
