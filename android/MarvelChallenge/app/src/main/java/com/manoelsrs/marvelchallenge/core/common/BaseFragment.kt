package com.manoelsrs.marvelchallenge.core.common

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    fun setEmptyView(emptyMessageView: TextView?, content: Boolean, message: Int) {
        emptyMessageView?.run {
            text = getString(message)
            visibility = if (content) View.VISIBLE else View.GONE
        }
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}