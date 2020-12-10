package br.com.mouzinho.marvelapp.view.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import br.com.mouzinho.domain.entity.character.CharacterResult
import br.com.mouzinho.domain.interactor.character.GetCharacters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class MainViewModel @ViewModelInject constructor(
    getCharacters: GetCharacters
) : ViewModel() {
    private val disposables = CompositeDisposable()

    init {
        getCharacters(10, 0)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is CharacterResult.Success -> {
                        Log.d("GetCharacters", result.data.toString())
                    }
                    is CharacterResult.Failure -> {
                        Log.d("GetCharacters", result.throwable.message ?: "Error")
                    }
                    is CharacterResult.Loading -> {
                        Log.d("GetCharacters", "Loading...")
                    }
                }
            }
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}