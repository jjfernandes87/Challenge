package com.manoelsrs.marvelchallenge.presentation.home.details

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import com.manoelsrs.marvelchallenge.core.extensions.showMessage
import com.manoelsrs.marvelchallenge.databinding.ActivityDetailsBinding
import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.presentation.home.details.adapters.DataListAdapter
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsContract {

    @Inject
    lateinit var presenter: DetailsPresenter

    private lateinit var binding: ActivityDetailsBinding
    private val comicsAdapter = DataListAdapter()
    private val seriesAdapter = DataListAdapter()
    private var dialog: AlertDialog? = null

    private val saveFavorite = BehaviorSubject.create<Unit>()
    override fun getFavoriteListener(): Observable<Unit> = saveFavorite

    private val deleteFavorite = BehaviorSubject.create<Unit>()
    override fun getDeleteFavoriteListener(): Observable<Unit> = deleteFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.presenter = presenter
        setComicsRecycler()
        setSeriesRecycler()
        presenter.onCreate(intent.getParcelableExtra(CHARACTER))

        rvComics.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvComics.canScrollHorizontally(1))
                    presenter.loadMoreComics()
            }
        })

        rvSeries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!rvSeries.canScrollHorizontally(1))
                    presenter.loadMoreSeries()
            }
        })
    }

    override fun showError(message: Int) {
        this.showMessage(
            title = R.string.common_error_title,
            message = message,
            buttonMessage = R.string.ok_button
        )
    }

    override fun showLoading() {
        dialog = showMessage(title = R.string.loading_title, message = R.string.loading_message)
    }

    override fun stopLoading() {
        dialog?.dismiss()
    }

    override fun askToSaveFavorite() {
        showMessage(
            listener = saveFavorite,
            title = R.string.favorite_title,
            message = R.string.save_favorite_message,
            positiveTitle = R.string.yes_button,
            negativeTitle = R.string.no_button,
            yes = Unit
        )
    }

    override fun askToDeleteFavorite() {
        showMessage(
            listener = deleteFavorite,
            title = R.string.favorite_title,
            message = R.string.delete_favorite_message,
            positiveTitle = R.string.yes_button,
            negativeTitle = R.string.no_button,
            yes = Unit
        )
    }

    private fun setComicsRecycler() {
        with(rvComics) {
            layoutManager = LinearLayoutManager(
                this@DetailsActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = comicsAdapter
        }
    }

    private fun setSeriesRecycler() {
        with(rvSeries) {
            layoutManager = LinearLayoutManager(
                this@DetailsActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = seriesAdapter
        }
    }

    override fun getPhotoView(): ImageView = ivCharacterPhoto

    override fun setComics(content: List<Data>) {
        comicsAdapter.setListItems(content)
    }

    override fun setSeries(content: List<Data>) {
        seriesAdapter.setListItems(content)
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    companion object {
        const val CHARACTER = "character"
    }
}