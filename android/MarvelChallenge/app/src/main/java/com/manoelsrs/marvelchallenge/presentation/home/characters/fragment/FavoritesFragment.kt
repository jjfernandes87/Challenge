package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.manoelsrs.marvelchallenge.R
import com.manoelsrs.marvelchallenge.core.common.BaseFragment
import com.manoelsrs.marvelchallenge.core.extensions.friendlyMessage
import com.manoelsrs.marvelchallenge.core.extensions.showMessage
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.ItemViewPagerAdapter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewState
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.FavoritesViewModel
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_characters.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class FavoritesFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: FavoritesViewModel

    @Inject
    lateinit var listener: Observable<String>

    private val adapter = ItemViewPagerAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        listener
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModel.updateItems(it)
                    watchViewModel(adapter)
                },
                { showError(context, R.string.common_error_message)  }
            ).also { addDisposable(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCharacters.layoutManager = GridLayoutManager(context, 2)
        rvCharacters.adapter = adapter
        adapter.setOnClickListener {
            startActivity<DetailsActivity>(DetailsActivity.CHARACTER to it)
        }
        swipeRefreshLayout.setOnRefreshListener { swipeRefreshLayout.isRefreshing = false }
        observeViewState()
        watchViewModel(adapter)
    }

    private fun watchViewModel(adapter: ItemViewPagerAdapter) {
        viewModel.characters.observe(this, Observer { item: PagedList<Character> ->
            adapter.submitList(item)
            setEmptyView(tvEmpy, item.isEmpty(), R.string.empty_favorites_message)
        })
    }

    private fun observeViewState() {
        viewModel.viewState().observe(this, Observer { viewState: CharactersViewState ->
            return@Observer when (viewState) {
                is CharactersViewState.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                    when(viewState.error) {
                        is HttpException -> viewState.error.friendlyMessage()
                        is TimeoutException -> R.string.timeout
                        is SocketTimeoutException -> R.string.timeout
                        else -> R.string.noConnection
                    }.apply { showError(context, this) }
                    Unit
                }
                is CharactersViewState.Loading -> {
                    swipeRefreshLayout.isRefreshing = viewState.isLoading
                }
            }
        })
    }

    private fun showError(context: Context?, message: Int) {
        context?.showMessage(
            title = R.string.common_error_title,
            message = message,
            buttonMessage = R.string.ok_button
        )
    }

    override fun onDetach() {
        dispose()
        super.onDetach()
    }
}