package com.manoelsrs.marvelchallenge.core.extensions

import android.content.Context
import androidx.appcompat.app.AlertDialog
import io.reactivex.subjects.BehaviorSubject

fun Context.showMessage(title: Int, message: Int): AlertDialog {
    return AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .show()
}

fun <V> Context.showMessage(
    listener: BehaviorSubject<V>,
    title: Int,
    message: Int,
    positiveTitle: Int,
    negativeTitle: Int,
    yes: V
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveTitle) { dialog, _ ->
            dialog.dismiss()
            listener.onNext(yes)
        }
        .setNegativeButton(negativeTitle) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}

fun Context.showMessage(
    title: Int,
    message: Int,
    buttonMessage: Int
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(buttonMessage) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}