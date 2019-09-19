package com.manoelsrs.marvelchallenge.presentation.home.details

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseActivity
import com.manoelsrs.marvelchallenge.databinding.ActivityDetailsBinding
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsContract {

    @Inject
    lateinit var presenter: DetailsPresenter

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.presenter = presenter
        presenter.onCreate(intent.getParcelableExtra(CHARACTER))
    }

    override fun getPhotoView(): ImageView = ivCharacterPhoto

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    companion object {
        const val CHARACTER = "character"
    }
}