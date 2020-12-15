package br.com.mouzinho.marvelapp.navigator

import androidx.fragment.app.Fragment
import br.com.mouzinho.marvelapp.navigator.Navigator.NavAction
import br.com.mouzinho.marvelapp.navigator.Navigator.NavAction.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * O [Navigator] é um objeto responsável por emitir e observar eventos encapsulados por [NavAction] que devem modificar
 * a stack de fragmentos da activity host sem criar novos acoplamentos.
 */
object Navigator {

    sealed class NavAction {
        data class Push(val fragment: Fragment, val title: String) : NavAction()
        data class PopUpTo(val fragmentClass: Class<*>) : NavAction()
        object PopUp : NavAction()
    }

    private val navigationPublisher = PublishSubject.create<NavAction>()

    fun observable(): Observable<NavAction> = navigationPublisher.hide()

    fun navigateTo(fragment: Fragment, title: String) =
        navigationPublisher.onNext(Push(fragment, title))

    fun popUp() = navigationPublisher.onNext(PopUp)

    fun popUpTo(fragmentClass: Class<*>) = navigationPublisher.onNext(PopUpTo(fragmentClass))
}