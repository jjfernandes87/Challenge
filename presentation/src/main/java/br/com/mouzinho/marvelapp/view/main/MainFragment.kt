package br.com.mouzinho.marvelapp.view.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.animations.DepthPageTransformer
import br.com.mouzinho.marvelapp.databinding.FragmentMainBinding
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        disposables.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        setupSearchView(menu.findItem(R.id.action_search).actionView as SearchView)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.queryTextChanges()
            .skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .map { viewModel.search(it) }
            .subscribe()
            .addTo(disposables)
    }

    private fun setupUi() {
        binding?.run {
            context?.let { context ->
                viewPager.adapter = MainViewPagerAdapter(context, childFragmentManager)
                viewPager.setPageTransformer(true, DepthPageTransformer())
                tabLayout.setupWithViewPager(viewPager)
            }
        }
    }
}