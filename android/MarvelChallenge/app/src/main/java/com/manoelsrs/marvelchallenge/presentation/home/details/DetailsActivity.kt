package com.manoelsrs.marvelchallenge.presentation.home.details

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import com.manoelsrs.marvelchallenge.databinding.ActivityDetailsBinding
import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.presentation.home.details.adapters.DataListAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsContract {

    @Inject
    lateinit var presenter: DetailsPresenter

    private lateinit var binding: ActivityDetailsBinding
    private val comicsAdapter = DataListAdapter()
    private val seriesAdapter = DataListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.presenter = presenter
        setComicsRecycler()
        setSeriesRecycler()
        presenter.onCreate(intent.getParcelableExtra(CHARACTER))
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