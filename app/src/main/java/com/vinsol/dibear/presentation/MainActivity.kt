package com.vinsol.dibear.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vinsol.dibear.R
import com.vinsol.dibear.data.sp.SharedPreferencesHelper
import com.vinsol.dibear.presentation.common.BaseActivity
import com.vinsol.dibear.presentation.beerList.BeerListFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    lateinit var listFragment: BeerListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI(savedInstanceState)
        setListeners()
    }

    private fun initUI(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            if(sharedPreferencesHelper.isFirstTime()) {

                showWelcome(this::addListFragment)

                sharedPreferencesHelper.saveIsFirstTime(false)
            } else {
                addListFragment()
            }
        }
    }

    private fun setListeners() {
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun inject() {
        app.mainComponent.createSubGraph().inject(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigation.selectedItemId) {
            return false
        }

        when (item.itemId) {
            R.id.nav_top_beers -> listFragment.updateTab(Tab.TOP_BEERS)
            R.id.nav_fav_beers -> listFragment.updateTab(Tab.FAVORITE_BEERS)
        }

        return true
    }

    private fun addListFragment(isFabList: Boolean = false) {
        listFragment = BeerListFragment.getInstance(showFavorite = isFabList)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, listFragment, "top beers")
            .commitNow()
        title = getString(R.string.top_beers)
    }

    private fun showWelcome(onDone: (Boolean) -> Unit) {
        frame_container.visibility = View.GONE
        navigation.visibility = View.GONE
        main_activity_welcome_text.visibility = View.VISIBLE
        main_activity_welcome_text.alpha = 0f

        main_activity_welcome_text.animate()
            .alpha(1f)
            .scaleX(4f)
            .scaleY(4f)
            .setDuration(2000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    frame_container.visibility = View.VISIBLE
                    navigation.visibility = View.VISIBLE
                    main_activity_welcome_text.visibility = View.GONE

                    onDone(false)
                }
            })
            .start()

    }
}

enum class Tab {
    TOP_BEERS, FAVORITE_BEERS
}
