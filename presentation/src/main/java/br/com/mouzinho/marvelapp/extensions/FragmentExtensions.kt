package br.com.mouzinho.marvelapp.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.mouzinho.marvelapp.R
import com.bumptech.glide.Glide

fun Fragment.showToast(message: String) {
    context?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showToast(@StringRes message: Int) {
    context?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showDialog(title: String, message: String) {
    context?.let {
        AlertDialog.Builder(it)
            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
            .setTitle(title)
            .setMessage(message)
            .show()
    }
}

fun Fragment.loadImage(url: String, imageView: ImageView, placeHolder: Int = R.drawable.thumb_placeholder) {
    context?.applicationContext?.let { appContext: Context ->
        Glide.with(appContext)
            .load(url)
            .placeholder(placeHolder)
            .into(imageView)
    }
}

fun Fragment.hideKeyboard() {
    context?.run {
        val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
