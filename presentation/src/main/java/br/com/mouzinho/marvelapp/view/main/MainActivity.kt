package br.com.mouzinho.marvelapp.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.ActivityMainBinding
import br.com.mouzinho.marvelapp.view.characters.CharactersPagingController
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val epoxyController by lazy { CharactersPagingController({}, {}) }
    private lateinit var binding: ActivityMainBinding
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setupUi()
        viewModel.loadCharacters()
        observeViewState()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun setupUi() {
        setupRecyclerViewWithEpoxy()
    }

    private fun observeViewState() {
        viewModel
            .stateObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::render)
            .addTo(disposables)
    }

    private fun render(state: MainState) {
        epoxyController.isLoading = state.loading
        epoxyController.submitList(state.characters)
    }

    private fun setupRecyclerViewWithEpoxy() {
        with(binding) {
            val layoutManager = GridLayoutManager(this@MainActivity, 2)
            epoxyController.spanCount = 2
            recyclerView.adapter = epoxyController.adapter
            layoutManager.spanSizeLookup = epoxyController.spanSizeLookup
            recyclerView.layoutManager = layoutManager
            recyclerView.setRemoveAdapterWhenDetachedFromWindow(true)
        }
    }
}