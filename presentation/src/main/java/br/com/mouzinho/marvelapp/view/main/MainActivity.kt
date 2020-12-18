package br.com.mouzinho.marvelapp.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.ActivityMainBinding
import br.com.mouzinho.marvelapp.extensions.hideKeyboard
import br.com.mouzinho.marvelapp.navigator.Navigator
import br.com.mouzinho.marvelapp.navigator.Navigator.NavAction.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setupUi()
        observeChildActions()
        observeNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun setupUi() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, MainFragment())
            .commit()
    }

    private fun observeChildActions() {
        viewModel.actionsObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { action ->
                when (action) {
                    is MainViewActions.ChangeToolbarTitle -> changeToolbarTitle(action.title)
                    is MainViewActions.SetupToolbar -> setupToolbar(action.toolbar)
                }
            }
            .addTo(disposables)
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun changeToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun observeNavigation() {
        Navigator.observable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { navAction ->
                when (navAction) {
                    is PopUp -> supportFragmentManager.popBackStack()
                    is Push -> onPushNewFragment(navAction)
                    is PopUpTo -> supportFragmentManager.popBackStack(navAction.fragmentClass.simpleName, 0)
                }
                hideKeyboard()
            }
            .addTo(disposables)
    }

    private fun onPushNewFragment(action: Push) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .addToBackStack(action.fragment::class.java.simpleName)
            .add(binding.fragmentContainer.id, action.fragment)
            .commit()
    }
}