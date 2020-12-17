package com.manoelsrs.marvelchallenge.presentation.home.characters

import com.manoelsrs.marvelchallenge.core.common.BasePresenter
import io.reactivex.subjects.BehaviorSubject

class CharactersPresenter(
    private val contract: CharactersContract,
    private val subject: BehaviorSubject<String>
) : BasePresenter() {

    var search: String = ""
        set(value) {
            field = value
            subject.onNext(value)
            notifyChange()
        }

    fun onCreate() {
        contract.configureTabs()
    }
}