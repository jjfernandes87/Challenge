package paixao.leonardo.marvel.heroes.feature.core.utils

import android.app.Service
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.di.instanceOrNull
import org.kodein.di.provider

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> DIAware.viewModel() = lazy {

    val factory = object : ViewModelProvider.Factory {
        override fun <Model : ViewModel> create(klass: Class<Model>) =
            direct.instance<VM>() as Model
    }

    val host = direct.instanceOrNull<FragmentActivity>()
        ?: throw IllegalStateException("Host Activity not attached on this graph")

    ViewModelProviders.of(host, factory).get(VM::class.java)
}

fun AppCompatActivity.selfInject(bindings: DI.MainBuilder.() -> Unit = {}) = DI.lazy {
    val parentDI = (applicationContext as DIAware).di

    extend(parentDI)

    bind<FragmentActivity>() with provider {
        this@selfInject
    }

    bindings.invoke(this)
}

fun Service.selfInject(bindings: DI.MainBuilder.() -> Unit = {}) = DI.lazy {
    val parentDI = (applicationContext as DIAware).di
    extend(parentDI)
    bindings.invoke(this)
}
