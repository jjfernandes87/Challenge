package br.com.mouzinho.marvelapp.view.main

import androidx.appcompat.widget.Toolbar


sealed class MainViewActions {
    data class ChangeToolbarTitle(val title: String) : MainViewActions()
    data class SetupToolbar(val toolbar: Toolbar) : MainViewActions()
}