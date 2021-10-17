package com.android.driveit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.driveit.R
import com.android.driveit.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bottomNav = binding.bottomNav

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupOnDestinationChangedListener()

        addShapeToBottomNav()

        addBadgeToBottomNav()
    }

    private fun addBadgeToBottomNav() {
        val badge = bottomNav.getOrCreateBadge(R.id.account)
        badge.isVisible = true
        badge.number = 4
        badge.badgeGravity = BadgeDrawable.BOTTOM_END
    }

    private fun addShapeToBottomNav() {
        val radius = resources.getDimension(R.dimen.cornerSizeBottomNav)

        val shapeDrawable: MaterialShapeDrawable = bottomNav.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel = shapeDrawable.shapeAppearanceModel.toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .setTopRightCorner(CornerFamily.ROUNDED, radius).build()
    }

    private fun setupOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.carListFragment -> {
                    bottomNav.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }
}