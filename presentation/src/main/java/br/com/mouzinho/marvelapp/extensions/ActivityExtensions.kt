package br.com.mouzinho.marvelapp.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun AppCompatActivity.hideKeyboard() {
    val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
    val focused = currentFocus ?: View(this)
    imm?.hideSoftInputFromWindow(focused.windowToken, 0)
}