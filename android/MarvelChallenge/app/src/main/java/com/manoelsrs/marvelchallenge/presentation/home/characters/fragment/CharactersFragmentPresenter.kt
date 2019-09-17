package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersFragmentPresenter(
    private val contract: CharactersFragmentContract,
    private val repository: Repository
) : BasePresenter() {

    fun onCreate() {
        repository.remote.characters.getCharacters(limit = 20, offset = 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val characters = it.body()?.data?.results?.map { result ->
                        Character(
                            id = result.id,
                            name = result.name.toUpperCase(),
                            photo = result.thumbnail.path,
                            photoExtension = result.thumbnail.extension
                        )
                    }
                    contract.updateCharacters(characters ?: listOf())
                },
                {}
            ).also { addDisposable(it) }
    }
}