package com.vinsol.dibear.presentation.beerDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinsol.dibear.R
import com.vinsol.dibear.data.entities.BeerData
import com.vinsol.dibear.data.repository.LocalRepo
import com.vinsol.dibear.data.repository.RemoteRepo
import com.vinsol.dibear.presentation.common.BaseActivity
import com.vinsol.dibear.presentation.common.ImageLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_beer_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.textColorResource
import timber.log.Timber
import javax.inject.Inject

class BeerDetailActivity : BaseActivity() {

    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var localRepo: LocalRepo
    @Inject lateinit var remoteRepo: RemoteRepo

    private lateinit var listAdapter: FoodPairingAdapter

    private var disposable: CompositeDisposable = CompositeDisposable()
    private var beerId: Int = -1
    private var beerData: BeerData? = null

    companion object {
        private const val BC_BEER_ID = "bundle_code:beer_id"

        fun getIntent(context: Context, beerId: Int): Intent {
            return context.intentFor<BeerDetailActivity>(BC_BEER_ID to beerId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)

        beerId = intent.getIntExtra(BC_BEER_ID, -1)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupView()
        initView()
        setListeners()
    }

    private fun setListeners() {
        remove_fab_button.setOnClickListener {
            beerData?.let {
                if(it.isFavorite) {
                    it.isFavorite = false
                    disposable.add(
                        localRepo.delete(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { updateViewForIsFavorite(false) }
                    )
                } else {
                    it.isFavorite = true
                    disposable.add(
                        localRepo.save(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { updateViewForIsFavorite(true) }
                    )
                }
            } ?: throw Exception("BeerData should not be null.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupView() {
        listAdapter = FoodPairingAdapter()
        details_tv_food_pairing_list.adapter = listAdapter
        details_tv_food_pairing_list.layoutManager = LinearLayoutManager(this)
    }

    private fun initView() {
        details_cl_container.visibility = View.GONE
        details_tv_error.visibility = View.GONE
        details_pb.visibility = View.VISIBLE
        remove_fab_button.visibility = View.GONE
        fab_label.visibility = View.GONE

        disposable.add(
            localRepo.get(beerId)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    if(!it.isPresent) Observable.error { IllegalArgumentException("MovieId must be present") }
                    else Observable.just(it)
                }
                .onErrorResumeNext(remoteRepo.getBeer(beerId))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    /*onNext*/ {
                        beerData = it.get()

                        details_cl_container.visibility = View.VISIBLE
                        details_tv_error.visibility = View.GONE
                        details_pb.visibility = View.GONE

                        with(it.get()) {
                            details_tv_name.text = name
                            details_tv_tag_line.text = tagLine
                            details_tv_abv_value.text = abv.toString()
                            details_tv_first_brewed_value.text = firstBrewed
                            details_tv_contributed_by_value.text = contributedBy
                            details_tv_description_text.text = description
                            details_tv_brewer_tips_text.text = brewersTips
                            listAdapter.submitList(foodPairing)

                            imageLoader.load(imageUrl, details_iv_beer)

                            title = name

                            updateViewForIsFavorite(isFavorite)
                        }
                    },
                    /*onError*/ {
                        Timber.e(it)
                    }
                )
        )
    }

    override fun inject() {
        app.mainComponent.inject(this)
    }

    private fun updateViewForIsFavorite(isFavorite: Boolean) {
        remove_fab_button.visibility = View.VISIBLE
        if(isFavorite) {
            remove_fab_button.text = getString(R.string.remove_from_fab)
            remove_fab_button.textColorResource = R.color.dark_grey

            fab_label.visibility = View.VISIBLE
        } else {
            remove_fab_button.text = getString(R.string.add_to_fab)
            remove_fab_button.textColorResource = R.color.colorAccent

            fab_label.visibility = View.GONE
        }
    }
}
