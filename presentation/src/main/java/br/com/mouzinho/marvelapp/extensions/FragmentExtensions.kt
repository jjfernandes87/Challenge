package br.com.mouzinho.marvelapp.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

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
