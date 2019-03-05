package com.vinsol.dibear.presentation.beerList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.vinsol.dibear.R
import com.vinsol.dibear.data.repository.LocalRepo
import com.vinsol.dibear.data.repository.RemoteRepo
import com.vinsol.dibear.presentation.Tab
import com.vinsol.dibear.presentation.beerDetail.BeerDetailActivity
import com.vinsol.dibear.presentation.common.BaseFragment
import com.vinsol.dibear.presentation.common.ImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.top_beers_fragment.*
import timber.log.Timber
import javax.inject.Inject

class BeerListFragment: BaseFragment() {

    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var remoteRepo: RemoteRepo
    @Inject lateinit var localRepo: LocalRepo

    private lateinit var listAdapter: TopBeersAdapter
    private var disposables: CompositeDisposable = CompositeDisposable()

    private lateinit var selectedTab: Tab

    companion object {
        private const val BC_SHOW_FAVORITE = "bundle_code:show_favorite"

        fun getInstance(showFavorite: Boolean): BeerListFragment {
            val fragment = BeerListFragment()

            fragment.arguments = Bundle().apply {
                putBoolean(BC_SHOW_FAVORITE, showFavorite)
            }

            return fragment
        }
    }

    override fun inject() {
        app.mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_beers_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isFab = arguments?.getBoolean(BC_SHOW_FAVORITE, false) ?: throw IllegalArgumentException("Show favorite is required")
        selectedTab = if(isFab) Tab.FAVORITE_BEERS else Tab.TOP_BEERS

        setupView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        disposables.clear()
    }

    private fun setupView() {
        listAdapter = TopBeersAdapter(imageLoader) {
            startActivity(BeerDetailActivity.getIntent(app, it.id))
        }
        top_beers_recycler_view.layoutManager = GridLayoutManager(app, 3)
        top_beers_recycler_view.adapter = listAdapter
    }

    private fun initView() {
        loadData()
    }

    private fun loadData() {
        top_beers_progress.visibility = View.VISIBLE
        val beerListObservable = if(selectedTab == Tab.FAVORITE_BEERS) localRepo.getAll() else remoteRepo.getTopBeers()

        disposables.add(
            beerListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    top_beers_progress.visibility = View.GONE
                    top_beers_recycler_view.visibility = View.GONE
                    top_beer_tv_error.visibility = View.VISIBLE
                    top_beer_tv_error.text = it.localizedMessage
                }.subscribe(
                    /*onNext*/{
                        top_beers_progress.visibility = View.GONE
                        top_beer_tv_error.visibility = View.GONE
                        top_beers_recycler_view.visibility = View.VISIBLE
                        listAdapter.addItems(it)
                    },
                    /*onError*/{
                        Timber.e(it)
                    }
                )
        )
    }

    fun updateTab(newSelectedTab: Tab) {
        if(selectedTab == newSelectedTab) return

        selectedTab = newSelectedTab
        loadData()
    }
}