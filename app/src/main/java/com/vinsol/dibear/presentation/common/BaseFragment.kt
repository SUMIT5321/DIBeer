package com.vinsol.dibear.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    val app: BeerApp by lazy { activity!!.application as BeerApp }

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }
}